package com.phpdragon.springboot.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @JmsListener(destination = "${spring.activemq.queue.height}")
    public void receiveHeightQueue(String msg) {
        LOGGER.debug("receiveHeightQueue message :{}" ,msg);
    }

    @JmsListener(destination = "${spring.activemq.queue.middle}")
    public void receiveMiddleQueue(String msg) {
        LOGGER.debug("receiveMiddleQueue message :{}" ,msg);
    }

    @JmsListener(destination = "${spring.activemq.queue.low}")
    public void receiveLowQueue(String msg) {
        LOGGER.debug("receiveLowQueue message :{}" ,msg);
    }
}