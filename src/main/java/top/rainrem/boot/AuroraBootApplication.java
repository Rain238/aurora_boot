package top.rainrem.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
// 开启配置属性绑定
@ConfigurationPropertiesScan
public class AuroraBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraBootApplication.class, args);
    }

}
