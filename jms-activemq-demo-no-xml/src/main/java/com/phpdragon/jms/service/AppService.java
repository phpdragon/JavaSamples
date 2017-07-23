package com.phpdragon.jms.service;

import com.phpdragon.jms.mq.MqConsumer;
import com.phpdragon.jms.mq.MqProducer;
import com.phpdragon.jms.task.SendTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private MqConsumer mqConsumer;

    public void run() {
        productMsg();
        consumerMsg();
    }

    public void productMsg() {
        Thread thread = new Thread(new SendTask(mqProducer));
        thread.start();
    }

    public void consumerMsg() {
        mqConsumer.startListener();
//        Thread thread = new Thread(new SendTask(mqProducer));
//        thread.start();
    }
}
