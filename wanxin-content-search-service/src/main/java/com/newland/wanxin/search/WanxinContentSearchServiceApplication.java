package com.newland.wanxin.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WanxinContentSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanxinContentSearchServiceApplication.class, args);
    }

}
