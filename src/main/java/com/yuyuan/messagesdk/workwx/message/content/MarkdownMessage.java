package com.yuyuan.messagesdk.workwx.message.content;

import lombok.Data;

/**
 * markdown 消息
 *
 * @author cq
 * @since 2023/12/21
 */
@Data
public class MarkdownMessage {

    private String content;

    /**
     * 构建 markdown 消息
     *
     * @param content 内容
     * @return {@link MarkdownMessage}
     */
    public static MarkdownMessage build(String content) {
        MarkdownMessage markdownMessage = new MarkdownMessage();
        int maxContextLength = 4096;
        if (content == null || content.getBytes().length > maxContextLength) {
            throw new IllegalArgumentException("Content 字段的最大长度不能超过 4096");
        }
        markdownMessage.setContent(content);
        return markdownMessage;
    }


}
