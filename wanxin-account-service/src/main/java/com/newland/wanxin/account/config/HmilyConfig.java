package com.newland.wanxin.account.config;

import org.dromara.hmily.spring.HmilyApplicationContextAware;
import org.dromara.hmily.spring.aop.SpringHmilyTransactionAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * hmily配置
 * Author: leell
 * Date: 2022/11/19 19:50:45
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class HmilyConfig {

    @Bean("hmilyTransactionAspect")
    public SpringHmilyTransactionAspect hmilyTransactionAspect() {
        return new SpringHmilyTransactionAspect();
    }

    @Bean("hmilyApplicationContextAware")
    public HmilyApplicationContextAware hmilyApplicationContextAware() {
        return new HmilyApplicationContextAware();
    }
}
