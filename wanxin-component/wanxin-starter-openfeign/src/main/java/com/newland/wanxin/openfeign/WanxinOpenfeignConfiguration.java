package com.newland.wanxin.openfeign;

import com.newland.wanxin.openfeign.properties.FeignProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(value = FeignProperties.class)
public class WanxinOpenfeignConfiguration {
}
