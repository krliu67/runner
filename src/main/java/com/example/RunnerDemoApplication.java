package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@Slf4j
@EnableOpenApi
@EnableCaching
public class RunnerDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(RunnerDemoApplication.class, args);
        log.info("项目启动成功");
        log.info("项目Swagger地址：http://localhost:8080/swagger-ui/");
    }

}
