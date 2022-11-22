package com.newland.wanxin.transaction.agent;

import com.newland.wanxin.api.consumer.model.BalanceDetailsDTO;
import com.newland.wanxin.api.consumer.model.ConsumerDTO;
import com.newland.wanxin.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
        C端服务代理
        */
@FeignClient(value = "consumer-service")
public interface ConsumerApiAgent {
    @GetMapping("/consumer/l/currConsumer/{mobile}")
    RestResponse<ConsumerDTO> getCurrConsumer(@PathVariable("mobile") String mobile);

    @GetMapping("/consumer/l/balances/{userNo}")
    RestResponse<BalanceDetailsDTO> getBalance(@PathVariable("userNo")
                                                              String userNo);
}
