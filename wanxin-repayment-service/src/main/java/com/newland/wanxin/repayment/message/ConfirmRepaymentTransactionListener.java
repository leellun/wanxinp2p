package com.newland.wanxin.repayment.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.wanxin.api.depository.model.RepaymentRequest;
import com.newland.wanxin.repayment.entity.RepaymentPlan;
import com.newland.wanxin.repayment.mapper.ReceivablePlanMapper;
import com.newland.wanxin.repayment.mapper.RepaymentPlanMapper;
import com.newland.wanxin.repayment.service.RepaymentPlanService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQTransactionListener(txProducerGroup = "PID_CONFIRM_REPAYMENT")
public class ConfirmRepaymentTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private RepaymentPlanService repaymentService;

    @Autowired
    private RepaymentPlanMapper planMapper;

    @Override
    //接收到消息后执行本地事务
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
       //1. 解析消息
        JSONObject jsonObject=JSON.parseObject(new String((byte[])msg.getPayload()));
        RepaymentPlan repaymentPlan=JSONObject.parseObject(jsonObject.getString("repaymentPlan"),RepaymentPlan.class);
        RepaymentRequest repaymentRequest=JSONObject.parseObject(jsonObject.getString("repaymentRequest"),RepaymentRequest.class);

        //2. 执行本地事务
        Boolean result = repaymentService.confirmRepayment(repaymentPlan,repaymentRequest);

        //3. 返回结果
        if(result){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    //进行事务状态回查
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //1.解析消息
        JSONObject jsonObject=JSON.parseObject(new String((byte[])msg.getPayload()));
        RepaymentPlan repaymentPlan=JSONObject.parseObject(jsonObject.getString("repaymentPlan"),RepaymentPlan.class);

        //2.回查事务状态
        RepaymentPlan newRepaymentPlan=planMapper.selectById(repaymentPlan.getId());

        //3.返回结果
        if(newRepaymentPlan!=null && newRepaymentPlan.getRepaymentStatus().equals("1")){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
