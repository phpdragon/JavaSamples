package com.phpdragon.jms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties", ignoreResourceNotFound = true)
public class AppConfig {
    @Value("${mq.queue.name}")
    private String mqQueueName;

    @Value("${mq.brokerURL}")
    private String brokerURL;

    @Value("${mq.useAsyncSend}")
    private boolean useAsyncSend;

    public String getMqQueueName() {
        return mqQueueName;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public boolean isUseAsyncSend() {
        return useAsyncSend;
    }
}
