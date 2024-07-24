package com.yuyuan.messagesdk.service;

import cn.hutool.core.util.ObjUtil;
import com.yuyuan.messagesdk.model.dto.Message;
import com.yuyuan.messagesdk.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.Map;

/**
 * 聚合的对外提供接口的消息发送类
 *
 * @author pine
 */
@ConditionalOnMissingBean(AggregatedMessageServiceImpl.class)
@Slf4j
public class AggregatedMessageServiceImpl implements MessageService {

    @Override
    public Class<? extends Message> getMessageClass() {
        return null;
    }

    public <T extends Message> void send(T message) {
        if (ObjUtil.isNull(message)) {
            throw new RuntimeException("message is null");
        }

        Class<? extends Message> realMessageClass = message.getClass();
        Map<String, MessageService> beansByType = SpringContextUtil.getBeansByType(MessageService.class);
        for (Map.Entry<String, MessageService> entry : beansByType.entrySet()) {
            MessageService messageServiceImpl = entry.getValue();
            if (ObjUtil.isNull(messageServiceImpl)) {
                continue;
            }
            if (!realMessageClass.equals(messageServiceImpl.getMessageClass())) {
                continue;
            }
            messageServiceImpl.checkMessageType(message);
            messageServiceImpl.doSend(message);
            // 执行数据库操作
            this.saveMessage(message);

            return;
        }
    }

    @Override
    public <T extends Message> void doSend(T message) {
        throw new RuntimeException("this method is not implemented yet");
    }

    public <T extends Message> void saveMessage(T message) {
    }

}
