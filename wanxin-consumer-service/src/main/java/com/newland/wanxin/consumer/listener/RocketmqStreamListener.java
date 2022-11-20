package com.newland.wanxin.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.newland.wanxin.api.depository.model.DepositoryConsumerResponse;
import com.newland.wanxin.consumer.service.ConsumerService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * rocketmq消息监听
 * Author: leell
 * Date: 2022/11/20 18:35:00
 */
@Component
public class RocketmqStreamListener {
    @Autowired
    private ConsumerService consumerService;

    @StreamListener(value = Sink.INPUT)
    public ConsumeConcurrentlyStatus getListener(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            Message message = msgs.get(0);
            String topic = message.getTopic();
            String tag = message.getTags();
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("从存管代理服务那里发来消息："+body);
            if(tag.equals("PERSONAL_REGISTER")){
                DepositoryConsumerResponse response = JSON.parseObject(body, DepositoryConsumerResponse.class);
                consumerService.modifyResult(response);
            }
            //if...

        }catch (Exception e){
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
