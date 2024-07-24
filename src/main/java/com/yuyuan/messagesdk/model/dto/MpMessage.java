package com.yuyuan.messagesdk.model.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.util.json.WxMpTemplateMessageGsonAdapter;

/**
 * @author pine
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MpMessage extends WxMpTemplateMessage implements Message {

    private static Gson gson = new GsonBuilder().registerTypeAdapter(MpMessage.class, new WxMpTemplateMessageGsonAdapter()).create();

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

}
