package com.newland.wanxin.account.agent;

import com.newland.wanxin.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信发送
 * Author: leell
 * Date: 2022/11/19 23:15:24
 */
@FeignClient(name = "wanxin-three-party-service")
public interface SmsSendApiAgent {
    @GetMapping(value = "/sms/sendCode")
    RestResponse sendCode(@RequestParam("phone") String phone, @RequestParam("content") String content);
}
