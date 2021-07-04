package cn.fxbin.framework.web;

import cn.fxbin.framework.web.core.logging.LoggingWebAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * WebAutoConfiguration
 *
 * @author fxbin
 * @version v1.0
 * @since 2021/7/4 11:10
 */
@Configuration(
        proxyBeanMethods = false
)
@ComponentScan(
        basePackages = {"cn.fxbin.framework.web"}
)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CustomizeWebAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public GlobalExceptionHandler globalExceptionHandler() {
//        return new GlobalExceptionHandler();
//    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingWebAspect loggingWebAspect() {
        return new LoggingWebAspect();
    }

}
