package com.phpdragon.springboot.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class MqTopic {

    @Value("${spring.activemq.topic.height}")
    private String heightMqTopic;

    @Value("${spring.activemq.topic.middle}")
    private String middleMqTopic;

    @Value("${spring.activemq.topic.low}")
    private String lowMqTopic;

    @Bean
    public Topic heightTopic() {
        return new ActiveMQTopic(heightMqTopic);
    }

    @Bean
    public Topic middleTopic() {
        return new ActiveMQTopic(middleMqTopic);
    }

    @Bean
    public Topic lowTopic() {
        return new ActiveMQTopic(lowMqTopic);
    }
}
