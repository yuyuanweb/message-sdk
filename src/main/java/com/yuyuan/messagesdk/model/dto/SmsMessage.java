package com.yuyuan.messagesdk.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author pine
 */
@Data
public class SmsMessage implements Message {

    /**
     * 电话号 set
     */
    private List<String> phoneNumberSet;

    /**
     * 模板 id
     */
    private String templateId;

    /**
     * 模板参数
     */
    private List<String> templateParamSet;

}
