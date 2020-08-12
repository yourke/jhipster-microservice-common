package com.jhipster.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.jhipster.common.filter.RequestOriginFilter;

/**
 * 请求来源Filter配置
 * 
 * @author yuenke
 * @date 2019/7/17 15:10
 */
@Configuration
public class RequestOriginConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public FilterRegistrationBean requestOriginFilterRegistration() {
        // 新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加指定的过滤器
        registration.setFilter(new RequestOriginFilter(env));
        // 设置过滤器的URL模式（针对模块间内部接口的校验）
        registration.addUrlPatterns("/api/inner/*");
        // 设置过滤器名称
        registration.setName("requestOriginFilter");
        // 设置过滤顺序，从小到大依次执行（放在springSecurityFilterChain之前）
        registration.setOrder(-101);
        return registration;
    }

}
