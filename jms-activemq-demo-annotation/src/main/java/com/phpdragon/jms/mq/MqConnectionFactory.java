package com.phpdragon.jms.mq;

import com.phpdragon.jms.config.AppConfig;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MqConnectionFactory {

    @Autowired
    private AppConfig appConfig;

    private static ActiveMQConnectionFactory factory;

    @PostConstruct
    public void init() {
        factory = new ActiveMQConnectionFactory(appConfig.getBrokerURL());
        factory.setUseAsyncSend(appConfig.isUseAsyncSend());
    }

    public ActiveMQConnectionFactory getFactory() {
        return factory;
    }
}
