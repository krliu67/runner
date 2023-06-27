package com.example.handler;

import com.example.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RabbitListener(queues = RabbitConfig.RUNNER_QUEUE)
public class RabbitMQHandle {
    @RabbitHandler
    void listener(Object message){
        log.info("收到来自"+RabbitConfig.RUNNER_QUEUE+"的消息");
        System.out.println("传来的message为 -> " + message.toString());
    }
}
