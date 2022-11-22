package com.newland.wanxin.party.controller;

import com.newland.wanxin.domain.RestResponse;
import com.newland.wanxin.party.service.SmsSendService;
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
    private SmsSendService smsSendService;

    /**
     * 提供给别的服务进行调用
     *
     * @param phone      电话号码
     * @param templateId 模板
     * @param params     参数
     * @return 发送结果
     */
    @ResponseBody
    @GetMapping(value = "/send")
    public RestResponse sendCode(@RequestParam("phone") String phone, @RequestParam("templateId") Integer templateId, @RequestParam("params") String[] params) {
        return smsSendService.sendCode(phone, templateId, params);
    }

}
