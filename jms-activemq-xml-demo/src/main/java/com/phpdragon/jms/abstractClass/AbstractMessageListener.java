package com.phpdragon.jms.abstractClass;

import com.phpdragon.jms.pojo.MessageInfo;

import javax.jms.JMSException;

public abstract class AbstractMessageListener {
    public abstract void handleMessage(MessageInfo info) throws JMSException;
}