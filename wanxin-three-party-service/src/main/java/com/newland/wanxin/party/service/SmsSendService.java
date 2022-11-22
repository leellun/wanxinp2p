package com.newland.wanxin.party.service;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.newland.wanxin.domain.RestResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * 短信发送
 * Author: leell
 * Date: 2022/11/21 22:40:17
 */
public class SmsSendService {
    @Value("${sms.qcloud.appId}")
    private int appId;

    @Value("${sms.qcloud.appKey}")
    private String appKey;

    @Value("${sms.qcloud.sign}")
    private String sign;

    public RestResponse sendCode(String phone, int templateId, String[] params) {
        try {
            SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
                    templateId, params, sign, "", "");  // 签名不能为空串
            return RestResponse.success();
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return RestResponse.validfail("发送失败");
    }
}
