package com.newland.wanxin.repayment.agent;

import com.newland.wanxin.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "wanxin-three-party-service")
public interface SmsSendApiAgent {

    @ResponseBody
    @GetMapping(value = "/sms/send")
    RestResponse sendCode(@RequestParam("phone") String phone, @RequestParam("templateId") Integer templateId, @RequestParam("params") String[] params);

}
