package com.phpdragon.jms.mq;

import com.phpdragon.jms.pojo.MessageInfo;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqProducer {

    @Value("${mq.queue.name}")
    private String queueName;

    @Autowired
    private MqConnectionFactory mqConnectionFactory;

    @Autowired
    private MqMessageConverter messageConverter;

    public JmsTemplate getJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();

        jmsTemplate.setConnectionFactory(mqConnectionFactory.getFactory());
        jmsTemplate.setDefaultDestination(new ActiveMQQueue(queueName));
        jmsTemplate.setReceiveTimeout(600);
        jmsTemplate.setMessageConverter(messageConverter);

        return jmsTemplate;
    }

    public void sendInfo(MessageInfo info) {
        try {
            getJmsTemplate().convertAndSend(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
