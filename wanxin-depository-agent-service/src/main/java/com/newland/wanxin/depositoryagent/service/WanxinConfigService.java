package com.newland.wanxin.depositoryagent.service;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
     * 银行存管系统服务地址
     * @return
     */
    public String getDepositoryUrl() {
        return getConfig("depository.url");
    }

    /**
     * 银行存管系统公钥
     * @return
     */
    public String getDepositoryPublicKey() {
        return getConfig("depository.publicKey");
    }

    /**
     * 万信P2P系统公钥
     * @return
     */
    public String getP2pPublicKey() {
        return getConfig("p2p.publicKey");
    }

    /**
     * 万信P2P系统 标识
     * @return
     */
    public String getP2pCode() {
        return getConfig("p2p.code");
    }

    /**
     * 万信P2P系统私钥
     * @return
     */
    public String getP2pPrivateKey() {
        return getConfig("p2p.privateKey");
    }
    private String getConfig(String name) {
        try {
            return config.getConfig(name, namespace, 3000);
        } catch (Exception e) {
            return null;
        }
    }

}
