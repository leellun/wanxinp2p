package com.newland.wanxin.repayment.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.wanxin.api.repayment.model.ProjectWithTendersDTO;
import com.newland.wanxin.repayment.service.RepaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class StartRepaymentMessageConsumer {

    @Autowired
    private RepaymentPlanService repaymentService;

    @StreamListener(Sink.INPUT)
    public void onMessage(String msg) {
        System.out.println("消费消息：" + msg);
        //1.解析消息
        final JSONObject jsonObject = JSON.parseObject(msg);
        ProjectWithTendersDTO projectWithTendersDTO =
                JSONObject.parseObject(jsonObject.getString("projectWithTendersDTO"), ProjectWithTendersDTO.class);

        //2.调用业务层执行本地事务
        repaymentService.startRepayment(projectWithTendersDTO);
    }
}
