package com.yuyuan.messagesdk.model.dto;

import lombok.Data;

/**
 * 企微消息
 *
 * @author cq
 * @since 2023/12/21
 */
@Data
public class WorkwxMessage implements Message {

    /**
     * 群聊 webhook
     */
    private String webhook;

    /**
     * 内容
     */
    private String content;

}
