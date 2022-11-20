package com.newland.wanxin.depository.service;

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
     * 获取银行存管系统公钥
     *
     * @return
     */
    public String getDepositoryPublicKey() {
        return this.getConfig("depository.publicKey");
    }

    /**
     * 获取银行存管系统私钥
     *
     * @return
     */
    public String getDepositoryPrivateKey() {
        return this.getConfig("depository.privateKey");
    }

    /**
     * 获取P2P平台公钥
     *
     * @return
     */
    public String getP2PPublicKey(String platformNo) {
        return this.getConfig("client-info.clients[" + platformNo + "].publicKey");
    }

    /**
     * 获取P2P平台公钥
     *
     * @return
     */
    public String getP2PNotifyUrl(String platformNo) {
        return this.getConfig("client-info.clients[" + platformNo + "].notifyUrl");
    }

    private String getConfig(String name) {
        try {
            return config.getConfig(name, namespace, 3000);
        } catch (Exception e) {
            return null;
        }
    }
}
