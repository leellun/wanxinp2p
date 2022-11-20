package com.newland.wanxin.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding({Sink.class})
@MapperScan("com.newland.wanxin.consumer.mapper")
public class Wanxinp2pConsumerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Wanxinp2pConsumerServiceApplication.class, args);
    }

}
