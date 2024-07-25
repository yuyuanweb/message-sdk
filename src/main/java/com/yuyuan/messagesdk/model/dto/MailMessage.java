package com.yuyuan.messagesdk.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 邮件消息
 *
 * @author pine
 */
@Data
public class MailMessage implements Message {

    /**
     * 接收地址
     */
    List<String> emailAddressSet;

    /**
     * 主题
     */
    String subject;

    /**
     * 内容
     */
    String content;

}
