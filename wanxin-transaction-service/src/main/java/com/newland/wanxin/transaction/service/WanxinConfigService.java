package com.newland.wanxin.transaction.service;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <P>
 * 本类用于获取配置文件中的配置, 封装成service方便调用
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/22
 */
@Service
public class WanxinConfigService {

    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;
    @NacosInjected
    private ConfigService config;

    /**
     * 获取流标时间, 单位天
     */
    public Integer getMiscarryDays() {
        return Integer.parseInt(this.getConfig("miscarry.days", "15"));
    }

    /**
     * 借款人佣金
     */
    public BigDecimal getCommissionBorrowerAnnualRate() {
        return new BigDecimal(this.getConfig("commission.borrower.annual.rate", null));
    }


    /**
     * 投资人佣金
     */
    public BigDecimal getCommissionInvestorAnnualRate() {
        return new BigDecimal(this.getConfig("commission.investor.annual.rate", null));
    }


    /**
     * 年化利率(平台佣金，利差)
     *
     * @return
     */
    public BigDecimal getCommissionAnnualRate() {
        return getCommissionBorrowerAnnualRate().add(getCommissionInvestorAnnualRate());
    }

    /**
     * 年化利率(借款人)
     *
     * @return
     */
    public BigDecimal getBorrowerAnnualRate() {
        return new BigDecimal(this.getConfig("borrower.annual.rate", null));
    }

    /**
     * 年化利率(投资人) = 借款人利率 - 平台佣金( 借款人佣金  +  投资人佣金 )
     *
     * @return
     */
    public BigDecimal getAnnualRate() {
        return getBorrowerAnnualRate().subtract(getCommissionAnnualRate());
    }

    /**
     * 最小投标金额
     *
     * @return
     */
    public BigDecimal getMiniInvestmentAmount() {
        // 如果配置文件中没有获取到, 这里使用默认值: 100.0, 有则使用配置文件中的
        return new BigDecimal(this.getConfig("mini.investment.amount", "100.0"));
    }

    private String getConfig(String name, String defaultValue) {
        try {
            String value = config.getConfig(name, namespace, 3000);
            return value == null ? defaultValue : value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
