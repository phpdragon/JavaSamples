package com.phpdragon.springboot.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class MqQueue {

    @Value("${spring.activemq.queue.height}")
    private String mqQueueHeight;

    @Value("${spring.activemq.queue.middle}")
    private String mqQueueMiddle;

    @Value("${spring.activemq.queue.low}")
    private String mqQueueLow;

    @Bean
    public Queue heightQueue() {
        return new ActiveMQQueue(mqQueueHeight);
    }

    @Bean
    public Queue middleQueue() {
        return new ActiveMQQueue(mqQueueMiddle);
    }

    @Bean
    public Queue lowQueue() {
        return new ActiveMQQueue(mqQueueLow);
    }
}
