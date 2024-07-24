package com.yuyuan.messagesdk;

import com.yuyuan.messagesdk.service.*;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;

/**
 * @author pine
 */
@Configuration
@ComponentScan
// 导入已经在 Ioc 中的对象
@Import({AggregatedMessageServiceImpl.class, EmailMessageServiceImpl.class, MpMessageServiceImpl.class, SmsMessageServiceImpl.class, WorkwxMessageServiceImpl.class})
public class MessageConfiguration {
}
