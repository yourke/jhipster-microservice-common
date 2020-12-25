package com.jhipster.common.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jhipster.common.client.OAuth2FeignClientInterceptor;

/**
 * 校验请求来源过滤器
 *
 * @author yonker
 * @date 2019/5/27 17:51
 */
public class RequestOriginFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(RequestOriginFilter.class);

    private final Environment env;

    public RequestOriginFilter(Environment env) {
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /*
         * TODO 或增加其他方式校验：
         * 1.如是否为注册表内的实例id，是否为局域网内ip
         * 2.在gateway中标记所有路过请求，若存在该标记说明不是内部模块调用
         */
        // 对于模块间接口，需要校验来源
        String instanceId = request.getHeader(OAuth2FeignClientInterceptor.INSTANCE_ID_HEADER);
        log.debug("Origin InstanceId: " + instanceId);

        // 当前请求是否有效（如果instanceId为空，说明非内部模块访问）
        boolean isValidRequest = StringUtils.isNotBlank(instanceId);
        // 当前模块是否处于开发模式，若是则直接放行
        isValidRequest = Arrays.asList(env.getActiveProfiles()).contains("dev") || isValidRequest;
        if (isValidRequest) {
            // 放行
            filterChain.doFilter(request, response);
        } else {
            // 拦截
            log.warn("非法来源请求，用户：{}，URI：{}，InstanceId：{}", request.getRemoteUser(), request.getRequestURI(), instanceId);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Illegal request origin");
        }
    }

}
