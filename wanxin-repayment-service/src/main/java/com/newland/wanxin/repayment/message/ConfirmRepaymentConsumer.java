package com.newland.wanxin.repayment.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.wanxin.api.depository.model.RepaymentRequest;
import com.newland.wanxin.repayment.entity.RepaymentPlan;
import com.newland.wanxin.repayment.model.ReplaymentSink;
import com.newland.wanxin.repayment.service.RepaymentPlanService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class ConfirmRepaymentConsumer {

    @Autowired
    private RepaymentPlanService repaymentService;

    @StreamListener(ReplaymentSink.INPUT)
    public void onMessage(String message) {
        //1.解析消息
        JSONObject jsonObject = JSON.parseObject(message);
        RepaymentPlan repaymentPlan = JSONObject.parseObject(jsonObject.getString("repaymentPlan"), RepaymentPlan.class);
        RepaymentRequest repaymentRequest = JSONObject.parseObject(jsonObject.getString("repaymentRequest"), RepaymentRequest.class);

        //2.执行本地业务
        repaymentService.invokeConfirmRepayment(repaymentPlan, repaymentRequest);
    }
}
