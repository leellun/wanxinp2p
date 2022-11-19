package com.newland.wanxin.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.newland.wanxin.account.mapper")
public class WanxinAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanxinAccountServiceApplication.class, args);
    }

}
