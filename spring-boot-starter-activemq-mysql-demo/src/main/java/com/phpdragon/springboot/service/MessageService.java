package com.phpdragon.springboot.service;

import com.alibaba.fastjson.JSON;
import com.phpdragon.springboot.activemq.MqProducer;
import com.phpdragon.springboot.db.entity.UserEntity;
import com.phpdragon.springboot.db.enums.UserSexEnum;
import com.phpdragon.springboot.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MqProducer producer;

    @Scheduled(fixedDelay = 3000)
    public void handlerMsg(){
        System.out.println("handlerMsg Thread id,name :" + Thread.currentThread().getId() + Thread.currentThread().getName());
    }

    @Scheduled(fixedDelay = 3000)
    public void handlerMsg3(){
        System.out.println("handlerMsg3 Thread id,name :" + Thread.currentThread().getId() + Thread.currentThread().getName());
    }

    public void run(ApplicationArguments args) {
        List<UserEntity> userList = userMapper.getAll();

        System.out.println(userList.get(0).toString());
        System.out.println(JSON.toJSONString(userList));

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("test");
        userEntity.setPassWord("abcde");
        userEntity.setUserSex(UserSexEnum.WOMAN);
        userEntity.setNickName("test");
        userMapper.insert(userEntity);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    long time = System.currentTimeMillis();
//                    producer.sendHeightQueue("lowQueue message " + time);
//                    producer.sendMiddleQueue("middleQueue message " + time);
//                    producer.sendLowQueue("lowQueue message " + time);
//
//                    producer.publishHeightTopic("heightTopic message " + time);
//                    producer.publishMiddleTopic("middleTopic message " + time);
//                    producer.publishLowTopic("lowTopic message " + time);
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).run();
    }
}
