package com.phpdragon.springboot.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MqConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(MqProducer.class);

    @JmsListener(destination = "${spring.activemq.queue.height}", containerFactory = "jmsListenerContainerQueue")
    public void receiveHeightQueue(String msg) {
        LOGGER.debug("receiveHeightQueue message :{}", msg);
    }

    @JmsListener(destination = "${spring.activemq.queue.middle}", containerFactory = "jmsListenerContainerQueue")
    public void receiveMiddleQueue(String msg) {
        LOGGER.debug("receiveMiddleQueue message :{}", msg);
    }

    @JmsListener(destination = "${spring.activemq.queue.low}", containerFactory = "jmsListenerContainerQueue")
    public void receiveLowQueue(String msg) {
        LOGGER.debug("receiveLowQueue message :{}", msg);
    }

    @JmsListener(destination = "${spring.activemq.topic.height}", containerFactory = "jmsListenerContainerTopic")
    public void receiveHeightTopic(String msg) {
        LOGGER.debug("receiveHeightTopic message :{}", msg);
    }

    @JmsListener(destination = "${spring.activemq.topic.middle}", containerFactory = "jmsListenerContainerTopic")
    public void receiveMiddleTopic(String msg) {
        LOGGER.debug("receiveMiddleTopic message :{}", msg);
    }

    @JmsListener(destination = "${spring.activemq.topic.low}", containerFactory = "jmsListenerContainerTopic")
    public void receiveLowTopic(String msg) {
        LOGGER.debug("receiveLowTopic message :{}", msg);
    }
}