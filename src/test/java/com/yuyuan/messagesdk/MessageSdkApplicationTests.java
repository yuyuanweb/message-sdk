package com.yuyuan.messagesdk;

import com.yuyuan.messagesdk.model.dto.WorkwxMessage;
import com.yuyuan.messagesdk.service.AggregatedMessageServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MessageSdkApplicationTests {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private AggregatedMessageServiceImpl aggregatedMessageServiceImpl;

    @Test
    void contextLoads() {

        // MpMessage mpMessage = new MpMessage();
        // mpMessage.setToUser("ovslX6lNcoojWwUGD63vQnGLjiTw");
        // mpMessage.setTemplateId("14W0bukzpTN-8L4zGgdApMnz7vqXQGZf0xAlpWfSy80");
        // mpMessage.setUrl("https://www.laoyujianli.com.com");
        // mpMessage.addData(new WxMpTemplateData("orderId", "123456"));
        // mpMessage.addData(new WxMpTemplateData("oriStatus", "订单超时"));
        // mpMessage.addData(new WxMpTemplateData("curStatus", "订单超🤩rfasdf时"));
        // messageServiceImpl.send(mpMessage);
        //
        //
        // MailMessage mailMessage = new MailMessage();
        // mailMessage.setEmailAddressSet(Collections.singletonList("18339461129@163.com"));
        // mailMessage.setSubject("mail test");
        // mailMessage.setContent("<p>hello, pine</p>");
        //
        // messageServiceImpl.send(mailMessage);
        //
        // SmsMessage smsMessage = new SmsMessage();
        // smsMessage.setPhoneNumberSet(Collections.singletonList("18339461129"));
        // smsMessage.setTemplateId("2180272");
        // smsMessage.setTemplateParamSet(Collections.singletonList("3451"));
        //
        // messageServiceImpl.send(smsMessage);

        WorkwxMessage workwxMessage = new WorkwxMessage();
        workwxMessage.setContent("??test哈哈哈😋");
        aggregatedMessageServiceImpl.send(workwxMessage);

    }

    @Test
    void mpTest() throws WxErrorException {
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        WxMpTemplateMessage message = WxMpTemplateMessage.builder()
                .toUser("ovslX6lNcoojWwUGD63vQnGLjiTw")
                .templateId("14W0bukzpTN-8L4zGgdApMnz7vqXQGZf0xAlpWfSy80")
                .build();
        message.addData(new WxMpTemplateData("orderId", "123456"));
        message.addData(new WxMpTemplateData("oriStatus", "订单超时"));
        message.addData(new WxMpTemplateData("curStatus", "订单超🤩rfasdf时"));
        templateMsgService.sendTemplateMsg(message);
    }

}
