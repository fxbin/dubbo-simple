package cn.fxbin.framework.core.exception;

import cn.fxbin.framework.core.logging.LoggerMessageFormat;
import cn.fxbin.framework.core.model.Result;
import cn.fxbin.framework.core.model.ResultCode;
import lombok.Getter;

/**
 * ServiceException, 业务异常
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 13:19
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7366961732679791481L;

    @Getter
    private int errcode;

    @Getter
    private String errmsg;

    public ServiceException(String message) {
        super(message);
        this.errcode = ResultCode.FAILURE.getCode();
    }

    public ServiceException(Integer errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getErrmsg());
        this.errcode = resultCode.getCode();
        this.errmsg = resultCode.getErrmsg();
    }

    public ServiceException(Result<?> result) {
        super(result.getErrmsg());
        this.errcode = result.getErrcode();
        this.errmsg = result.getErrmsg();
    }

    public ServiceException(ResultCode resultCode, String errmsg, Object... args) {
        super(LoggerMessageFormat.format(errmsg, args));
        this.errcode = resultCode.getCode();
        this.errmsg = LoggerMessageFormat.format(errmsg, args);
    }

    public ServiceException(String msg, Object... args) {
        super(LoggerMessageFormat.format(msg, args));
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

