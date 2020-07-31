package com.jhipster.common.config;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jhipster.common.client.OAuth2FeignClientConfiguration;
import com.jhipster.common.client.QueryMapRequestInterceptor;

import feign.Logger;
import feign.RequestInterceptor;

/**
 * Feign的全局配置，在被主程序@ComponentScan扫描后，被所有FeignClient共享
 * 
 * @author yuanke
 * @date 2019/7/15 21:04
 */
@Configuration
@EnableFeignClients(defaultConfiguration = {OAuth2FeignClientConfiguration.class,
        FeignGlobalConfiguration.class}, basePackages = "com.jhipster.common")
public class FeignGlobalConfiguration {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FeignGlobalConfiguration.class);

    /**
     * 默认Feign是不打印任何日志的，需要通过代码配置或属性配置的方式开启，Feign有四种日志级别：
     * NONE【性能最佳，适用于生产】：不记录任何日志（默认值）。
     * BASIC【适用于生产环境追踪问题】：仅记录请求方法、URL、响应状态代码以及执行时间。
     * HEADERS：记录BASIC级别的基础上，记录请求和响应的header。
     * FULL【比较适用于开发及测试环境定位问题】：记录请求和响应的header、body和元数据。
     */
    @Bean
    public Logger.Level logger() {
        // debug级别下，开启feign详细日志
        return logger.isDebugEnabled() ? Logger.Level.HEADERS : Logger.Level.NONE;
    }

    @Bean(name = "queryMapRequestInterceptor")
    public RequestInterceptor getQueryMapRequestInterceptor() {
        // 增加对GET方法传递动态POJO参数的支持，spring-cloud-openfeign升级到2.1.0.RC1版本后可用官方的@SpringQueryMap替换
        return new QueryMapRequestInterceptor();
    }

    // TODO Hystrix配置
}
