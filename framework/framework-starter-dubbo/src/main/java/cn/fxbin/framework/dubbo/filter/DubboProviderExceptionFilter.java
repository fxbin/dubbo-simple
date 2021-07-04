package cn.fxbin.framework.dubbo.filter;

import cn.fxbin.framework.core.exception.GlobalException;
import cn.fxbin.framework.core.exception.ServiceException;
import cn.fxbin.framework.core.model.ResultCode;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Type;

/**
 * DubboProviderExceptionFilter
 *
 * @author fxbin
 * @version v1.0
 * @since 2021/7/5 0:06
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class DubboProviderExceptionFilter implements Filter, Filter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                // 1. 转换异常
                Throwable exception = appResponse.getException();
                // 1.1 参数校验异常
                if (exception instanceof ConstraintViolationException) {
                    exception = this.constraintViolationExceptionHandler((ConstraintViolationException) exception);
                    // 1. ServiceException 业务异常，因为不会有序列化问题，所以无需处理
                } else if (exception instanceof ServiceException) {
                    // 1.3 其它异常，转换成 GlobalException 全局异常，避免可能存在的反序列化问题 TODO
                } else {
                    exception = this.defaultExceptionHandler(exception, invocation);
                    assert exception != null;
                }
                // 2. 根据不同的方法 schema 返回结果
                // 2.1 如果是 ServiceException 异常，并且返回参数类型是 Result 的情况，则将转换成 Result 返回
                if (isReturnCommonResult(invocation) && exception instanceof ServiceException) {
                    appResponse.setException(null); // 一定要清空异常
                    appResponse.setValue(cn.fxbin.framework.core.model.Result.failure((ServiceException) exception));
                    // 2.2 如果是 GlobalException 全局异常，则直接抛出 TODO
                } else {
                    // TODO 优化点：尝试修改成 RpcException
                    appResponse.setException(exception);
                }
            } catch (Throwable e) {
                log.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }

    private boolean isReturnCommonResult(Invocation invocation) {
        if (!(invocation instanceof RpcInvocation)) {
            return false;
        }
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        Type[] returnTypes = rpcInvocation.getReturnTypes();
        if (returnTypes.length == 0) {
            return false;
        }
        Type returnType = returnTypes[0];
        if (!(returnType instanceof Class)) {
            return false;
        }
        Class returnClass = (Class) returnType;
        return returnClass == cn.fxbin.framework.core.model.Result.class;
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    private ServiceException constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return new ServiceException(ResultCode.BAD_REQUEST.getCode(),
                String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    private ServiceException defaultExceptionHandler(Throwable exception, Invocation invocation) {
        log.error("[defaultExceptionHandler][service({}) method({}) params({}) 执行异常]",
                invocation.getTargetServiceUniqueName(), invocation.getMethodName(), invocation.getArguments(), exception);
        // 如果已经是 GlobalException 全局异常，直接返回即可
        if (exception instanceof ServiceException) {
            return (ServiceException) exception;
        }
        return new ServiceException(ResultCode.INTERNAL_SERVER_ERROR);
    }

    private String buildDetailMessage(Throwable exception, Invocation invocation) {
        return String.format("Service(%s) Method(%s) 发生异常(%s)",
                invocation.getTargetServiceUniqueName(), invocation.getMethodName(), ExceptionUtil.getRootCauseMessage(exception));
    }
}
