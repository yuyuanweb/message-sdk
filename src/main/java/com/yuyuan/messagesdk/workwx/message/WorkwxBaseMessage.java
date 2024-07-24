package com.yuyuan.messagesdk.workwx.message;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yuyuan.messagesdk.workwx.message.type.MessageTypeEnum;
import lombok.Data;

/**
 * 消息
 *
 * @author cq
 * @since 2023/12/21
 */
@Data
public class WorkwxBaseMessage<T> {
    private String msgType;
    private T content;

    public WorkwxBaseMessage(MessageTypeEnum msgType, T content) {
        this.msgType = msgType.getValue();
        this.content = content;
    }

    public String toJsonStr() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("msgtype", msgType);
        jsonObject.set(msgType, content);
        return JSONUtil.toJsonStr(jsonObject);
    }
}
