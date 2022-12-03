package com.newland.newland.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: leell
 * Date: 2022/12/3 20:54:21
 */
@ConfigurationProperties(prefix = "wanxin.security")
public class SecurityProperties {
    private String[] permitUrls;

    public void setPermitUrls(String[] permitUrls) {
        this.permitUrls = permitUrls;
    }

    public String[] getPermitUrls() {
        return permitUrls;
    }
}
