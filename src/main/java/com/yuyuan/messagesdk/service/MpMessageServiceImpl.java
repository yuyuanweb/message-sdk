package com.yuyuan.messagesdk.service;

import com.yuyuan.messagesdk.model.dto.Message;
import com.yuyuan.messagesdk.model.dto.MpMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信服务号模版消息发送类
 *
 * @author pine
 */
@Service
public class MpMessageServiceImpl implements MessageService {

    @Resource
    private WxMpService wxMpService;

    public final Class<MpMessage> MESSAGE_CLASS = MpMessage.class;

    @Override
    public Class<? extends Message> getMessageClass() {
        return MESSAGE_CLASS;
    }

    public void sendMpMessage(MpMessage message) {
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        try {
            templateMsgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Message> void doSend(T message) {
        MpMessage mpMessage = MESSAGE_CLASS.cast(message);
        this.sendMpMessage(mpMessage);
    }

}
