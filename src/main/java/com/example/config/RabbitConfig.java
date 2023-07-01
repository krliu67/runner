package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class RabbitConfig implements InitializingBean {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public final static String RUNNER_EXCHANGE = "RUNNER_EXCHANGE";
    public final static String RUNNER_QUEUE = "RUNNER_QUEUE";
    public final static String RUNNER_ROUTING_KEY = "RUNNER_ROUTING_KEY";
    /**
     * 消息
     * 1.创建交换机 Fanout类型的exchange会忽略routingKey，所以配置中无法设置routingKey
     * 2.创建队列
     * 3.通过路由键绑定交换机和队列
     */
    @Bean
    public FanoutExchange getRunnerExchange() {
        return new FanoutExchange(RUNNER_EXCHANGE,true,false);
    }
    @Bean
    public Queue getRunnerQueue(){
        return new Queue(RUNNER_QUEUE,true,false,false);
    }
    @Bean
    public Binding queueBinding(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
//        return BindingBuilder.bind(queue).to(fanoutExchange).with();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}