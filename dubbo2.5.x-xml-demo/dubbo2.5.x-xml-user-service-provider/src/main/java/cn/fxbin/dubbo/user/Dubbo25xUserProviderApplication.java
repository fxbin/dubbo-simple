package cn.fxbin.dubbo.user;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * UserProviderApplication
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/6/30 13:36
 */
@ImportResource({"classpath:dubbo-provider.xml"})
@SpringBootApplication
public class Dubbo25xUserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dubbo25xUserProviderApplication.class, args);
        Main.main(args);
    }

}
