package com.yuyuan.messagesdk.workwx.message.content;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 纯文本接口
 *
 * @author zwb
 */
@Data
public class TextMessage {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 被提及者 userId 列表
     */
    private List<String> mentionedList;

    /**
     * 被提及者电话号码列表
     */
    private List<String> mentionedMobileList;

    /**
     * 构建文本消息
     *
     * @param content             内容
     * @param mentionedList       提及列表
     * @param mentionedMobileList 提及手机列表
     * @param mentionAll          是否全部提及
     */
    public static TextMessage build(String content, List<String> mentionedList, List<String> mentionedMobileList, Boolean mentionAll) {
        TextMessage textMessage = new TextMessage();
        if (mentionAll) {
            String all = "@all";

            if (CollUtil.isNotEmpty(mentionedMobileList)) {
                mentionedMobileList.add(all);
            } else {
                mentionedMobileList = CollUtil.newArrayList(all);
            }
        }
        textMessage.setContent(content);
        textMessage.setMentionedList(mentionedList);
        textMessage.setMentionedMobileList(mentionedMobileList);
        return textMessage;
    }

    public static TextMessage build(String content) {
        return build(content, null, null, false);
    }

    public static void main(String[] args) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent("234");
        textMessage.setMentionedList(new ArrayList<String>() {{
            add("123");
        }});
        textMessage.setMentionedMobileList(new ArrayList<String>() {{
            add("hhh");
        }});

        System.out.println(JSONUtil.toJsonStr(BeanUtil.beanToMap(textMessage, true, false)));
    }
}
