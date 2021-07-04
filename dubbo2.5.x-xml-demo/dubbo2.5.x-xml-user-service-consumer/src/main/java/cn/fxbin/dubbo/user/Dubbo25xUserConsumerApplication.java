package cn.fxbin.dubbo.user;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * UserConsumerApplication
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 11:39
 */
@ImportResource({"classpath:dubbo-consumer.xml"})
@SpringBootApplication
public class Dubbo25xUserConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dubbo25xUserConsumerApplication.class, args);
        Main.main(args);
    }

}
