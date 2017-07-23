package com.phpdragon.springboot.service;

import com.phpdragon.springboot.activemq.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private Producer producer;

    public void run(ApplicationArguments args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    long time = System.currentTimeMillis();
                    producer.sendHeightQueue("lowQueue message " + time);
                    producer.sendMiddleQueue("middleQueue message " + time);
                    producer.sendLowQueue("lowQueue message " + time);

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
