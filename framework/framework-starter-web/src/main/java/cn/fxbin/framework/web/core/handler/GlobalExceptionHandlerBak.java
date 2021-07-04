//package cn.fxbin.framework.web.core.handler;
//
//import cn.fxbin.framework.core.exception.GlobalException;
//import cn.fxbin.framework.core.exception.ServiceException;
//import cn.fxbin.framework.core.logging.SysExceptionLogDTO;
//import cn.fxbin.framework.core.model.Result;
//import cn.fxbin.framework.core.util.ExceptionUtils;
//import cn.fxbin.framework.core.util.JsonUtils;
//import cn.fxbin.framework.core.util.StringUtils;
//import cn.fxbin.framework.core.util.WebUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.skywalking.apm.toolkit.trace.TraceContext;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.util.Assert;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.xml.bind.ValidationException;
//import java.util.Date;
//import java.util.UUID;
//
//import static cn.fxbin.framework.core.model.ResultCode.*;
//
///**
// * GlobalExceptionHandler
// *
// * <p>
// *     全局异常处理器，将 Exception 翻译成 Result + 对应的异常编号 (TODO, 默认 -1)
// * </p>
// *
// * @author fxbin
// * @version v1.0
// * @since 2021/7/4 11:01
// */
//@SuppressWarnings("rawtypes")
//@Slf4j
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @Value("${spring.application.name:app}")
//    private String application;
//
//    /**
//     * 处理 SpringMVC 请求参数缺失
//     *
//     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
//     */
//    @ExceptionHandler(value = MissingServletRequestParameterException.class)
//    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
//        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
//        return Result.failure(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
//    }
//
//    /**
//     * 处理 SpringMVC 请求参数类型错误
//     *
//     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
//     */
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public Result methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
//        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
//        return Result.failure(BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
//    }
//
//    /**
//     * 处理 SpringMVC 参数校验不正确
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
//        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
//        FieldError fieldError = ex.getBindingResult().getFieldError();
//        // 断言，避免告警
//        assert fieldError != null;
//        return Result.failure(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
//    }
//
//    /**
//     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
//     */
//    @ExceptionHandler(BindException.class)
//    public Result bindExceptionHandler(BindException ex) {
//        log.warn("[handleBindException]", ex);
//        FieldError fieldError = ex.getFieldError();
//        // 断言，避免告警
//        assert fieldError != null;
//        return Result.failure(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
//    }
//
//    /**
//     * 处理 Validator 校验不通过产生的异常
//     */
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    public Result constraintViolationExceptionHandler(ConstraintViolationException ex) {
//        log.warn("[constraintViolationExceptionHandler]", ex);
//        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
//        return Result.failure(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
//    }
//
//    /**
//     * 处理 SpringMVC 请求地址不存在
//     *
//     * 注意，它需要设置如下两个配置项：
//     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
//     * 2. spring.mvc.static-path-pattern 为 /statics/**
//     */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Result noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
//        log.warn("[noHandlerFoundExceptionHandler]", ex);
//        return Result.failure(NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
//    }
//
//    /**
//     * 处理 SpringMVC 请求方法不正确
//     *
//     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
//     */
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public Result httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
//        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
//        return Result.failure(METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
//    }
//
//    /**
//     * 处理业务异常 ServiceException
//     *
//     * 例如说，用户手机号已存在，等等
//     */
//    @ExceptionHandler(value = ServiceException.class)
//    public Result serviceExceptionHandler(ServiceException ex) {
//        log.info("[serviceExceptionHandler]", ex);
//        return Result.failure(ex.getErrcode(), ex.getMessage());
//    }
//
//    /**
//     * 处理全局异常 ServiceException
//     *
//     * 例如说，Dubbo 请求超时，调用的 Dubbo 服务系统异常
//     *
//     * TODO HttpServletRequest 参数是否有必要添加，待测试，WebUtils.getRequest();
//     */
//    @ExceptionHandler(value = GlobalException.class)
//    public Result globalExceptionHandler(GlobalException ex) {
//        // 系统异常时，才打印异常日志
//        if (INTERNAL_SERVER_ERROR.getCode() == ex.getErrcode()) {
//            // 插入异常日志
//            this.createExceptionLog(ex);
//        } else {
//            // 普通全局异常，打印 info 日志即可
//            log.info("[globalExceptionHandler]", ex);
//        }
//        return Result.failure(ex);
//    }
//
//    /**
//     * 处理 Dubbo Consumer 本地参数校验时，抛出的 ValidationException 异常
//     */
//    @ExceptionHandler(value = ValidationException.class)
//    public Result validationException(ValidationException ex) {
//        log.warn("[constraintViolationExceptionHandler]", ex);
//        // 无法拼接明细的错误信息，因为 Dubbo Consumer 抛出 ValidationException 异常时，是直接的字符串信息，且人类不可读
//        return Result.failure(BAD_REQUEST.getCode(), "请求参数不正确");
//    }
//
//    /**
//     * 处理系统异常，兜底处理所有的一切
//     */
//    @ExceptionHandler(value = Exception.class)
//    public Result defaultExceptionHandler(Throwable ex) {
//        log.error("[defaultExceptionHandler]", ex);
//        // 插入异常日志
//        this.createExceptionLog(ex);
//        // 返回 ERROR Result
//        return Result.failure(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getErrmsg());
//    }
//
//    public void createExceptionLog(Throwable e) {
//        // 插入异常日志
//        SysExceptionLogDTO exceptionLog = new SysExceptionLogDTO();
//        try {
//            // 初始化 exceptionLog
//            initExceptionLog(exceptionLog, e);
//            // 执行插入 exceptionLog
//            createExceptionLog(exceptionLog);
//        } catch (Throwable th) {
//            log.error("[createExceptionLog][插入访问日志({}) 发生异常({})", JsonUtils.toJson(exceptionLog), ExceptionUtils.getRootCauseMessage(th));
//        }
//    }
//
//    // TODO 优化点：接入数据库存储，后续可以增加事件， 暂时只打印日志
//    @Async
//    public void createExceptionLog(SysExceptionLogDTO exceptionLog) {
//        try {
//            log.warn("exception log:{}", JsonUtils.toJson(exceptionLog));
////            sysExceptionLogRpc.createSysExceptionLog(exceptionLog);
//        } catch (Throwable th) {
//            log.error("[addAccessLog][插入异常日志({}) 发生异常({})", JsonUtils.toJson(exceptionLog), ExceptionUtils.getRootCauseMessage(th));
//        }
//    }
//
//    private void initExceptionLog(SysExceptionLogDTO exceptionLog, Throwable e) {
//        // 设置账号编号
////        exceptionLog.setUserId();
////        exceptionLog.setUserType();
//        // 设置异常字段
//        exceptionLog.setExceptionName(e.getClass().getName());
//        exceptionLog.setExceptionMessage(ExceptionUtils.getMessage(e));
//        exceptionLog.setExceptionRootCauseMessage(ExceptionUtils.getRootCauseMessage(e));
//        exceptionLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
//        StackTraceElement[] stackTraceElements = e.getStackTrace();
//        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
//        StackTraceElement stackTraceElement = stackTraceElements[0];
//        exceptionLog.setExceptionClassName(stackTraceElement.getClassName());
//        exceptionLog.setExceptionFileName(stackTraceElement.getFileName());
//        exceptionLog.setExceptionMethodName(stackTraceElement.getMethodName());
//        exceptionLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
//        // 设置其它字段
//        exceptionLog.setTraceId(getTraceId())
//                .setApplication(application)
//                // TODO 提升：如果想要优化，可以使用 Swagger 的 @ApiOperation 注解。
//                .setUri(WebUtils.getRequestUrl())
//                .setQueryString(WebUtils.getQueryString())
//                .setMethod(WebUtils.getRequest().getMethod())
//                .setUserAgent(WebUtils.getUserAgent(WebUtils.getRequest()))
//                .setIp(WebUtils.getIpAddr(WebUtils.getRequest()))
//                .setExceptionTime(new Date());
//    }
//
//    private String getTraceId() {
//
//        // 1. 通过 SkyWalking 获取链路编号
//        // 2. 接入 sleuth ，可以直接获取 traceId
//        // 3. 随机生成 UUID
//        // TODO 压测，3 的方式可能存在问题
//
//        String traceId = "";
//        try {
//            traceId = TraceContext.traceId();
//            if (StringUtils.isNotBlank(traceId)) {
//                return traceId;
//            }
//            traceId = MDC.get("traceId");
//            if (StringUtils.isNotBlank(traceId)) {
//                return traceId;
//            }
//        } catch (Throwable ignore) {
//
//        }
//        return StringUtils.getUUID();
//    }
//
//}
