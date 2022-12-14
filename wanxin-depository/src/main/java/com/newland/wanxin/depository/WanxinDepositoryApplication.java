package com.newland.wanxin.depository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.newland.wanxin.depository.mapper")
public class WanxinDepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanxinDepositoryApplication.class, args);
    }

}
