package com.newland.wanxin.repayment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: leell
 * Date: 2022/11/21 22:53:54
 */
@Getter
@Component
public class SmsConfig {
    @Value("${sms.qcloud.templateId}")
    private Integer templateId;
}
