package com.phpdragon.jms.logic;

import com.alibaba.fastjson.JSON;
import com.phpdragon.jms.pojo.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MsgPushLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPushLogic.class);

    public boolean pushMsg(MessageInfo info) {
        LOGGER.info("MsgPushLogic pushMsg: {}", JSON.toJSONString(info));
        return true;
    }
}
