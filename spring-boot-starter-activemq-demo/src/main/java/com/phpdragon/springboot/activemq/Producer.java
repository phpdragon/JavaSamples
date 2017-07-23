package com.phpdragon.springboot.activemq;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer{

    private static Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue heightQueue;

    @Autowired
    private Queue middleQueue;

    @Autowired
    private Queue lowQueue;

    public void sendHeightQueue(String msg) {
        LOGGER.debug("sendHeightQueue :{}" ,msg);
        this.jmsMessagingTemplate.convertAndSend(this.heightQueue, msg);
    }

    public void sendMiddleQueue(String msg) {
        LOGGER.debug("sendMiddleQueue :{}" ,msg);
        this.jmsMessagingTemplate.convertAndSend(this.middleQueue, msg);
    }

    public void sendLowQueue(String msg) {
        LOGGER.debug("sendLowQueue :{}" ,msg);
        this.jmsMessagingTemplate.convertAndSend(this.lowQueue, msg);
    }

}