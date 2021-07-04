package cn.fxbin.framework.core.exception;

import cn.fxbin.framework.core.logging.LoggerMessageFormat;
import cn.fxbin.framework.core.model.Result;
import cn.fxbin.framework.core.model.ResultCode;
import lombok.Getter;

/**
 * UtilException, 工具类异常
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 13:19
 */
public class UtilException extends RuntimeException {

    private static final long serialVersionUID = 7366961732679791481L;

    @Getter
    private int errcode;

    @Getter
    private String errmsg;

    public UtilException(String message) {
        super(message);
        this.errcode = ResultCode.FAILURE.getCode();
    }

    public UtilException(Integer errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public UtilException(Result<?> result) {
        super(result.getErrmsg());
        this.errcode = result.getErrcode();
        this.errmsg = result.getErrmsg();
    }

    public UtilException(ResultCode resultCode, String errmsg, Object... args) {
        super(LoggerMessageFormat.format(errmsg, args));
        this.errcode = resultCode.getCode();
        this.errmsg = LoggerMessageFormat.format(errmsg, args);
    }

    public UtilException(String msg, Object... args) {
        super(LoggerMessageFormat.format(msg, args));
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    protected UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

