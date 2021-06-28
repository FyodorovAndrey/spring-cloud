package com.nvfredy.notification.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String QUEUE_DEPOSIT = "deposit.notify.queue";
    private static final String EXCHANGE_DEPOSIT = "deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "deposit.routing.key";

    @Bean
    public Queue depositQueue() {
        return new Queue(QUEUE_DEPOSIT);
    }

    @Bean
    public TopicExchange depositExchange() {
        return new TopicExchange(EXCHANGE_DEPOSIT);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder
                .bind(depositQueue())
                .to(depositExchange())
                .with(ROUTING_KEY_DEPOSIT);
    }
}
