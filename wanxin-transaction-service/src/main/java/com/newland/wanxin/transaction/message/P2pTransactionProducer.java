package com.newland.wanxin.transaction.message;


import com.alibaba.fastjson.JSONObject;
import com.newland.wanxin.api.repayment.model.ProjectWithTendersDTO;
import com.newland.wanxin.transaction.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class P2pTransactionProducer {
    @Autowired
    private Source source;

    public void updateProjectStatusAndStartRepayment(Project project,
                                                     ProjectWithTendersDTO projectWithTendersDTO) {
        //1.构造消息
        JSONObject object=new JSONObject();
        object.put("project",project);
        object.put("projectWithTendersDTO",projectWithTendersDTO);
        Message<String> msg = MessageBuilder.withPayload(object.toJSONString()).build();
        //2.发送消息
        source.output().send(msg);
    }
}
