package com.phpdragon.springboot.service;

import com.phpdragon.springboot.activemq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MqProducer producer;

    public void run(ApplicationArguments args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long time = System.currentTimeMillis();
                    producer.sendHeightQueue("lowQueue message " + time);
                    producer.sendMiddleQueue("middleQueue message " + time);
                    producer.sendLowQueue("lowQueue message " + time);

                    producer.publishHeightTopic("heightTopic message " + time);
                    producer.publishMiddleTopic("middleTopic message " + time);
                    producer.publishLowTopic("lowTopic message " + time);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();
    }
}
