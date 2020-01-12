package com.jhipster.common.client;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;

import com.netflix.appinfo.ApplicationInfoManager;

import feign.RequestInterceptor;
import io.github.jhipster.security.uaa.LoadBalancedResourceDetails;

/**
 * 用户认证Interceptor配置
 * 
 * @author yuanke
 * @date 2019/7/15 20:41
 */
public class OAuth2FeignClientConfiguration {

    private final LoadBalancedResourceDetails loadBalancedResourceDetails;
    private final ApplicationInfoManager applicationInfoManager;


    public OAuth2FeignClientConfiguration(LoadBalancedResourceDetails loadBalancedResourceDetails, ApplicationInfoManager applicationInfoManager) {
        this.loadBalancedResourceDetails = loadBalancedResourceDetails;
        this.applicationInfoManager = applicationInfoManager;
    }

    @Bean(name = "oauth2RequestInterceptor")
    public RequestInterceptor getOAuth2RequestInterceptor() {
        // 直接注入DefaultOAuth2ClientContext会报错，因为不在spring管理中？
        return new OAuth2FeignClientInterceptor(new DefaultOAuth2ClientContext(), loadBalancedResourceDetails, applicationInfoManager);
    }
}
