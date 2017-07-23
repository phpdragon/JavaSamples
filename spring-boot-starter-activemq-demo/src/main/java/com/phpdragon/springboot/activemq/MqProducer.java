package com.phpdragon.springboot.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class MqProducer {

    private static Logger LOGGER = LoggerFactory.getLogger(MqProducer.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue heightQueue;

    @Autowired
    private Queue middleQueue;

    @Autowired
    private Queue lowQueue;

    @Autowired
    private Topic heightTopic;

    @Autowired
    private Topic middleTopic;

    @Autowired
    private Topic lowTopic;

    public void sendHeightQueue(String msg) {
        LOGGER.debug("sendHeightQueue :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.heightQueue, msg);
    }

    public void sendMiddleQueue(String msg) {
        LOGGER.debug("sendMiddleQueue :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.middleQueue, msg);
    }

    public void sendLowQueue(String msg) {
        LOGGER.debug("sendLowQueue :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.lowQueue, msg);
    }

    public void publishHeightTopic(String msg) {
        LOGGER.debug("sendLowTopic :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.heightTopic, msg);
    }

    public void publishMiddleTopic(String msg) {
        LOGGER.debug("sendLowTopic :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.middleTopic, msg);
    }

    public void publishLowTopic(String msg) {
        LOGGER.debug("sendLowTopic :{}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.lowTopic, msg);
    }
}