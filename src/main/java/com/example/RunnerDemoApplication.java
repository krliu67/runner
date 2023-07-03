package com.example;

import com.example.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication
@Slf4j
@EnableOpenApi
@EnableCaching
public class RunnerDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(RunnerDemoApplication.class, args);
        String projUrl = new ServerConfig().getUrl();
        System.out.println("~ ❤ ~ 项目启动成功 ~ ❤ ~");
        System.out.println("项目Swagger地址:"+projUrl+":8080/swagger-ui/");
        System.out.println("项目druid监控地址:"+projUrl+":8080/druid");
        System.out.println("项目rabbitmq监控地址:"+projUrl+":15672");
    }

}
