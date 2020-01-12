package com.jhipster.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jhipster.common.client.OAuth2FeignClientInterceptor;

/**
 * 校验请求来源过滤器
 *
 * @author yuanke
 * @date 2019/5/27 17:51
 */
public class RequestOriginFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(RequestOriginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 对于模块间接口，需要校验来源
        String instanceId = request.getHeader(OAuth2FeignClientInterceptor.INSTANCE_ID_HEADER);
        log.debug("Origin InstanceId: " + instanceId);
        // 如果instanceId为空，说明非内部模块访问 TODO 或增加其他方式校验，如是否为注册表内的实例id，是否为局域网内ip
        if (StringUtils.isBlank(instanceId)) {
            log.warn("非法来源请求，URI：{}，InstanceId：{}", request.getRequestURI(), instanceId);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Illegal request origin");
            return;
        }
        // 放行
        filterChain.doFilter(request, response);
    }

}
