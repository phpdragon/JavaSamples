package com.phpdragon.jms.mq;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * 消息监听容器
 * Created by phpdragon on 2016/8/8
 */
public class MessageListenerContainer extends DefaultMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerContainer.class);

    private double rate = 10000.0;

    private RateLimiter rateLimiter;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        if (rate >= 1) {
            this.rate = rate;
        }
        this.rateLimiter = RateLimiter.create(this.rate);
    }

    @Override
    protected boolean receiveAndExecute(Object invoker, Session session, MessageConsumer consumer) throws JMSException {
        //消费MQ限速
        if (!rateLimiter.tryAcquire()) {
            return false;
        }

        return super.receiveAndExecute(invoker, session, consumer);
    }
}