package com.newland.wanxin.repayment;

import com.newland.wanxin.repayment.model.ReplaymentSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients
@EnableBinding({Sink.class, Source.class, ReplaymentSink.class})
public class WanxinRepaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanxinRepaymentServiceApplication.class, args);
    }

}
