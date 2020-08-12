package com.jhipster.common.client;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.netflix.appinfo.ApplicationInfoManager;

import feign.RequestTemplate;

/**
 * 认证信息Interceptor
 *
 * @author yuenke
 * @date 2019/7/15 16:08
 */
public class OAuth2FeignClientInterceptor extends OAuth2FeignRequestInterceptor {

    /** 自定义header属性InstanceId */
    public static final String INSTANCE_ID_HEADER = "X-Instance-Id";

    private final ApplicationInfoManager applicationInfoManager;

    OAuth2FeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource,
            ApplicationInfoManager applicationInfoManager) {
        super(oAuth2ClientContext, resource);
        this.applicationInfoManager = applicationInfoManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        /*
         * 1.填充用户认证信息
         */
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            // 有用户认证信息
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            template.header(AUTHORIZATION, String.format("%s %s", BEARER, details.getTokenValue()));
        } else {
            // 无用户认证信息，使用客户端认证信息
            super.apply(template);
        }

        /*
         * 2.标记消费者的来源
         */
        template.header(INSTANCE_ID_HEADER, applicationInfoManager.getInfo().getId());
    }
}
