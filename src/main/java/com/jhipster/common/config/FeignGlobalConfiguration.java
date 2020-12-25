package com.jhipster.common.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jhipster.common.client.OAuth2FeignClientConfiguration;
import com.jhipster.common.client.QueryMapRequestInterceptor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.hystrix.HystrixFeign;

/**
 * Feign的全局配置，在被主程序@ComponentScan扫描后，被所有FeignClient共享
 * 
 * @author yonker
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

    /**
     * 集成了Hystrix的Fegin客户端，增加全局默认hystrix配置，避免高并发下调用失败，无需修改各模块hystrix配置<br/>
     * 参考于{@link FeignClientsConfiguration}中HystrixFeignConfiguration<br/>
     * 另外，可用hystrix的@DefaultProperties实现类级别的默认配置
     */
    @Configuration
    @ConditionalOnClass({HystrixCommand.class, HystrixFeign.class})
    protected static class HystrixFeignConfiguration {

        /*
         * 问题：在此方法上想加个@ConditionalOnMissingBean注解，本地验证正常。
         * 但在开发环境部署时，以portal模块为例，加了该注解就进不了此方法，有feign在此之前被加载了？先去掉该注解，后续有必要再修改
         */
        @Bean
        @Scope("prototype")
        // @ConditionalOnMissingBean
        @ConditionalOnProperty(name = "feign.hystrix.enabled", havingValue = "true", matchIfMissing = false)
        public Feign.Builder feignHystrixBuilder() {
            logger.info("hystrix feign client has configured!");
            return HystrixFeign.builder().setterFactory((target, method) -> {
                String groupKey = target.name();
                String commandKey = Feign.configKey(target.type(), method);
                return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        // 设置线程池默认值
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                // 允许线程池大小自动动态调整（默认false）
                                .withAllowMaximumSizeToDivergeFromCoreSize(true)
                                // 最大线程数（默认10）
                                .withMaximumSize(50)
                                // 非核心线程的空闲时间min（默认1）
                                .withKeepAliveTimeMinutes(10));
            });
        }
    }
}
