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
        log.info("~ ❤ ~ 项目启动成功 ~ ❤ ~");
        log.info("项目Swagger地址:"+projUrl+"/swagger-ui/");
        log.info("项目druid监控地址:"+projUrl+"/druid");
        log.info("项目rabbitmq监控地址:"+projUrl+"/druid");
    }

}
