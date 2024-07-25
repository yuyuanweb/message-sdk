package com.yuyuan.messagesdk.service;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.yuyuan.messagesdk.model.dto.MailMessage;
import com.yuyuan.messagesdk.model.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 邮件消息发送类
 *
 * @author pine
 */
@Service
@Slf4j
public class EmailMessageServiceImpl implements MessageService {

    @Value("${tencent.ses.user}")
    private String emailUser;

    @Value("${tencent.ses.password}")
    private String emailPassword;

    @Value("${tencent.ses.nickName}")
    private String emailNick;

    public final Class<MailMessage> MESSAGE_CLASS = MailMessage.class;

    @Override
    public Class<? extends Message> getMessageClass() {
        return MESSAGE_CLASS;
    }

    /**
     * 发送电子邮件
     *
     * @param mailMessage 邮件信息
     */
    public void sendEmailMessage(MailMessage mailMessage) {
        List<String> emailAddressSet = mailMessage.getEmailAddressSet();
        String subject = mailMessage.getSubject();
        String content = mailMessage.getContent();
        try {
            MailAccount account = null;

            // 邮箱配置
            String host = "smtp.qcloudmail.com";
            String port = "465";

            // 发送信息
            account = new MailAccount();
            account.setHost(host);
            account.setPort(new BigDecimal(port).intValue());
            account.setAuth(true);
            account.setFrom(String.format("%s <%s>", emailNick, emailUser));
            account.setUser(emailUser);
            account.setPass(emailPassword);
            account.setSslEnable(true);
            account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
            account.setSocketFactoryFallback(false);
            account.setSocketFactoryPort(new BigDecimal(port).intValue());

            account.setStarttlsEnable(true);
            MailUtil.send(account, emailAddressSet, subject, content, true);


        } catch (Exception e) {
            log.error("sendEmailMessage failed, e: ", e);
        }
    }

    @Override
    public <T extends Message> void doSend(T message) {
        MailMessage mailMessage = MESSAGE_CLASS.cast(message);
        this.sendEmailMessage(mailMessage);
    }

}
