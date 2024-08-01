package com.yuyuan.messagesdk;

import com.yuyuan.messagesdk.model.dto.WorkwxMessage;
import com.yuyuan.messagesdk.service.AggregatedMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MessageSdkApplicationTests {

    @Resource
    private AggregatedMessageServiceImpl aggregatedMessageServiceImpl;

    @Test
    void contextLoads() {

        WorkwxMessage workwxMessage = new WorkwxMessage();
        workwxMessage.setContent("??test哈哈哈😋");
        aggregatedMessageServiceImpl.send(workwxMessage);

    }

}
