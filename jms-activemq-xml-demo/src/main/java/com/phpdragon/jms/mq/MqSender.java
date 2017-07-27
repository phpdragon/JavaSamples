package com.phpdragon.jms.mq;

import com.phpdragon.jms.pojo.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendInfo(MessageInfo info) {
        try {
            jmsTemplate.convertAndSend(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}