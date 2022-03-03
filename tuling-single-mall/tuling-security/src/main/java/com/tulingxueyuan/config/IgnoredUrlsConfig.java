package com.tulingxueyuan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/3 15:30
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoredUrlsConfig {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
