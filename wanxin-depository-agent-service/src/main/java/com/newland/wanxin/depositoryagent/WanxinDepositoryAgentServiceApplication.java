package com.newland.wanxin.depositoryagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class WanxinDepositoryAgentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanxinDepositoryAgentServiceApplication.class, args);
    }

}
