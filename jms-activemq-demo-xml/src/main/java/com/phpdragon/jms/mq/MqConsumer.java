package com.phpdragon.jms.mq;

import com.phpdragon.jms.abstractClass.AbstractMessageListener;
import com.phpdragon.jms.logic.MsgPushLogic;
import com.phpdragon.jms.pojo.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class MqConsumer extends AbstractMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqConsumer.class);

    @Autowired
    private MsgPushLogic msgPushLogic;

    public void handleMessage(MessageInfo info) throws JMSException {
        //TODO : 如果抛异常，该信息是否会取消消费
        if (!msgPushLogic.pushMsg(info)) {
            //TODO: 如果不会回滚，应该再反插入一条消息到MQ
            throw new JMSException("压入队列失败");
        }
    }

}