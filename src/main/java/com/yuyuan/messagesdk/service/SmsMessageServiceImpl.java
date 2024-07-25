package com.yuyuan.messagesdk.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.yuyuan.messagesdk.model.dto.Message;
import com.yuyuan.messagesdk.model.dto.SmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手机短信消息发送类
 *
 * @author gulihua
 */
@Slf4j
@Service
public class SmsMessageServiceImpl implements MessageService {

    @Value("${tencent.sms.secretId}")
    private String secretId;
    @Value("${tencent.sms.secretKey}")
    private String secretKey;
    @Value("${tencent.sms.sdkAppId}")
    private String sdkAppId;
    @Value("${tencent.sms.signName}")
    private String signName;

    public final Class<SmsMessage> MESSAGE_CLASS = SmsMessage.class;

    @Override
    public Class<? extends Message> getMessageClass() {
        return MESSAGE_CLASS;
    }

    /**
     * 发送短信
     *
     * @param smsMessage 短信
     */
    public void sendSmsMessage(SmsMessage smsMessage) {
        List<String> phoneNumberSet = smsMessage.getPhoneNumberSet();
        String templateId = smsMessage.getTemplateId();
        List<String> templateParamSet = smsMessage.getTemplateParamSet();

        // 标记短信是否全部发送成功
        boolean allSuccess = true;
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
            Credential cred = new Credential(secretId, secretKey);

            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            // 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            // 实例化一个客户端配置对象，可以指定超时时间等配置
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
            SendSmsRequest req = new SendSmsRequest();

            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            req.setSmsSdkAppId(sdkAppId);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名 */
            req.setSignName(signName);

            /* 模板 ID: 必须填写已审核通过的模板 ID */
            req.setTemplateId(templateId);

            /* 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空 */
            req.setTemplateParamSet(templateParamSet.toArray(new String[0]));

            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
            req.setPhoneNumberSet(phoneNumberSet.toArray(new String[0]));

            // 通过 client 对象调用 SendSms 方法发起请求。
            SendSmsResponse res = client.SendSms(req);
            for (SendStatus sendStatus : res.getSendStatusSet()) {
                Boolean success = "Ok".equalsIgnoreCase(sendStatus.getCode());
                log.info("手机号为{}的用户短信发送状态:{}", sendStatus.getPhoneNumber(), success);
            }

        } catch (TencentCloudSDKException e) {
            log.error("sendSMSMessage failed, e: ", e);
        }
    }


    @Override
    public <T extends Message> void doSend(T message) {
        this.sendSmsMessage(MESSAGE_CLASS.cast(message));
    }
}
