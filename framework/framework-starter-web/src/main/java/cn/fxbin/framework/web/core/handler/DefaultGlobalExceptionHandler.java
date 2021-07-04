package cn.fxbin.framework.web.core.handler;

import cn.fxbin.framework.core.exception.ServiceException;
import cn.fxbin.framework.core.model.Result;
import cn.fxbin.framework.core.model.ResultCode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 * DefaultGlobalExceptionHandler
 *
 * @author fxbin
 * @version v1.0
 * @since 2020/7/2 13:38
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public Result<String> exceptionHandler(Exception exception) {
        log.warn("[Exception]", exception);
        return Result.failure(exception.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    public Result<String> exceptionHandler(ServiceException ex) {
        log.warn("[ServiceException]", ex);
        return Result.failure((ex.getErrcode() == 0 ? ResultCode.FAILURE.getCode() : ex.getErrcode()), ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<String> bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("MethodArgumentNotValidException: {}", exception.getMessage());
        return Result.failure(fieldErrors.get(0).getDefaultMessage());
    }

    @ExceptionHandler({BindException.class})
    public Result<String> bindExceptionHandler(BindException exception) {
        @SuppressWarnings("ConstantConditions") String defaultMessage = exception.getGlobalError().getDefaultMessage();
        log.warn("BindException: {}", exception.getMessage());
        return Result.failure(ObjectUtils.isEmpty(defaultMessage) ? ResultCode.BAD_REQUEST.getErrmsg() : defaultMessage);
    }

    @ExceptionHandler({JsonParseException.class})
    public Result<String> exceptionHandler(JsonParseException exception) {
        log.warn("JsonParseException: {}", exception.getMessage());
        return Result.failure(exception.getMessage());
    }

    @ExceptionHandler({JsonMappingException.class})
    public Result<String> exceptionHandler(JsonMappingException exception) {
        log.warn("JsonMappingException: {}", exception.getMessage());
        return Result.failure(exception.getMessage());
    }

}
