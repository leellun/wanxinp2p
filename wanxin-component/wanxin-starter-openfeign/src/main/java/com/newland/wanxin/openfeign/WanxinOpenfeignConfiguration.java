package com.newland.wanxin.openfeign;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Author: leell
 * Date: 2022/11/27 18:58:00
 */
@Configuration
@ComponentScan
@EnableHystrix
public class WanxinOpenfeignConfiguration {
}
