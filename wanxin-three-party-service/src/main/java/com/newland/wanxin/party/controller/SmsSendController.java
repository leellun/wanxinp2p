package com.newland.wanxin.party.controller;

import com.newland.wanxin.domain.RestResponse;
import com.newland.wanxin.party.component.SmsComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/sms")
public class SmsSendController {

    @Resource
    private SmsComponent smsComponent;

    /**
     * 提供给别的服务进行调用
     *
     * @param phone
     * @param content
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/sendCode")
    public RestResponse sendCode(@RequestParam("phone") String phone, @RequestParam("content") String content) {

        //发送验证码
//        smsComponent.sendCode(phone,code);
        System.out.println(phone + ":" + content);
        return RestResponse.success();
    }

}
