package com.yuyuan.messagesdk.workwx.message.type;


import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息类别枚举
 *
 * @author cq
 * @since 2023/12/21
 */
public enum MessageTypeEnum {

    TEXT("文字类型", "text"),
    MARKDOWN("markdown", "markdown"),
    ;

    private final String text;

    private final String value;

    MessageTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return {@link List}<{@link String}>
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 值
     * @return {@link MessageTypeEnum}
     */
    public static MessageTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (value.equals(messageTypeEnum.getValue())) {
                return messageTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
