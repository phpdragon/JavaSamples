package com.phpdragon.jms.task;

import com.alibaba.fastjson.JSON;
import com.phpdragon.jms.mq.MqProducer;
import com.phpdragon.jms.pojo.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendTask.class);

    private MqProducer mqProducer;

    public SendTask(MqProducer mqProducer) {
        this.mqProducer = mqProducer;
    }

    public void run() {
        while (true) {
            try {
                MessageInfo info = new MessageInfo();

                LOGGER.info("插入一个任务到MQ, 任务信息为:" + JSON.toJSONString(info));
                mqProducer.sendInfo(info);

                System.out.println("插入一个任务到MQ, 任务信息为:" + JSON.toJSONString(info));

                Thread.sleep(3000);
            } catch (Exception e) {
                LOGGER.error("TaskSearchTask任务出现未知错误", e);
            }
        }
    }

}
