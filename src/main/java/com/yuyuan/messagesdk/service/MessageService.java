package com.yuyuan.messagesdk.service;

import com.yuyuan.messagesdk.model.dto.Message;

/**
 * @author pine
 */
public interface MessageService {



    /**
     * 获取消息类型
     */
    Class<? extends Message> getMessageClass();

    /**
     * 执行发送信息逻辑
     */
    <T extends Message> void doSend(T message);

    default <T extends Message> void checkMessageType(T message) {
        Class<? extends Message> messageClass = getMessageClass();
        if (message.getClass() != messageClass) {
            throw new RuntimeException("message class is not " + messageClass.getName());
        }
    }

}
