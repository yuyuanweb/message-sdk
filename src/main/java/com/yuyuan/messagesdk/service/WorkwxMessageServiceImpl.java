package com.yuyuan.messagesdk.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yuyuan.messagesdk.model.dto.Message;
import com.yuyuan.messagesdk.model.dto.WorkwxMessage;
import com.yuyuan.messagesdk.workwx.message.WorkwxBaseMessage;
import com.yuyuan.messagesdk.workwx.message.content.TextMessage;
import com.yuyuan.messagesdk.workwx.message.type.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 企微消息发送类
 *
 * @author yuyuan
 */
@Slf4j
@Component
public class WorkwxMessageServiceImpl implements MessageService {

    @Value("${notify.workwx.webhook:}")
    private String webhook;

    public final Class<WorkwxMessage> MESSAGE_CLASS = WorkwxMessage.class;

    @Override
    public Class<? extends Message> getMessageClass() {
        return MESSAGE_CLASS;
    }

    private void sendText(WorkwxMessage workwxMessage) {
        String webhook = workwxMessage.getWebhook();
        String content = workwxMessage.getContent();
        if (StrUtil.isBlank(content)) {
            throw new RuntimeException("message content is empty");
        }
        send(MessageTypeEnum.TEXT, TextMessage.build(content), webhook);
    }

    public <T> void send(MessageTypeEnum messageTypeEnum, T content, String tempWebhook) {
        if (StrUtil.isAllBlank(webhook, tempWebhook)) {
            log.error("没有找到配置项中的webhook,请检查：1.是否在application.yml中填写webhook 2.是否在spring环境下运行");
            throw new IllegalArgumentException("没有找到配置项中的webhook");
        }
        WorkwxBaseMessage<T> WorkwxMessage = new WorkwxBaseMessage<>(messageTypeEnum, content);
        String messageJson = WorkwxMessage.toJsonStr();
        String thisWebhook = webhook;
        if (StrUtil.isNotBlank(tempWebhook)) {
            thisWebhook = tempWebhook;
        }
        try (HttpResponse response = HttpRequest.post(thisWebhook)
                .body(messageJson)
                .execute()) {
            if (response.isOk()) {
                log.info("message send");
            } else {
                log.error("消息发送失败，响应数据：{}", response.body());
            }
        } catch (Exception e) {
            log.error("发送消息出现错误", e);
        }
    }

    @Override
    public <T extends Message> void doSend(T message) {
        WorkwxMessage workwxMessage = MESSAGE_CLASS.cast(message);
        this.sendText(workwxMessage);
    }

}