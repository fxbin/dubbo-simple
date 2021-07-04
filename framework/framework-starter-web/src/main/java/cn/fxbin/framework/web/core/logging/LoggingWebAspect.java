package cn.fxbin.framework.web.core.logging;

import cn.fxbin.framework.core.util.JsonUtils;
import cn.fxbin.framework.core.util.StringUtils;
import cn.fxbin.framework.core.util.WebUtils;
import cn.hutool.core.date.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * LoggingWebAspect
 *
 * @author fxbin
 * @version v1.0
 * @since 2020/5/19 17:28
 */
@Slf4j
@Aspect
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LoggingWebAspect extends AbstractLogging {

    @Pointcut(value = "(@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController))"
    )
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        return logGenerate(point);
    }

    private Object logGenerate(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = WebUtils.getRequest();
        HttpServletResponse response = WebUtils.getResponse();
        long startNs = SystemClock.now();

        // 请求参数
        Object[] args = point.getArgs();
        String requestBody = JsonUtils.isJsonSerialize(args) ? JsonUtils.toJson(args) : StringUtils.utf8Str(args);

        Object responseObj = null;
        try {

            log.info("RequestUri: 「{}」, IP: 「{}」, Method:「{}」, Param:「{}」",
                    request.getRequestURI(), WebUtils.getIpAddr(request), request.getMethod(), requestBody);

            responseObj = point.proceed();
            return responseObj;
        } catch (Exception e) {
            log.warn("exception : " + e.getMessage(), e);
            throw e;
        } finally {
            long endNs = SystemClock.now();
            long tookMs = TimeUnit.MILLISECONDS.toMillis(endNs - startNs);
            String responseBody = JsonUtils.isJsonSerialize(responseObj) ? JsonUtils.toJson(responseObj) : StringUtils.utf8Str(responseObj);
            log.info("ResponseBody: 「{}」, ResponseStatus: 「{}」, Consuming: 「{}ms」",
                    responseBody, response.getStatus(),tookMs);
        }
    }



}
