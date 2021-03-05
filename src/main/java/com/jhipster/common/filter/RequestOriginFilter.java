package com.jhipster.common.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jhipster.common.constant.HeaderConstant;

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
         * 此处校验方式多种多样，根据需求自定义，如要限制模块间接口只能内部调用，参考方案：
         * 1.在gateway中标记所有路过请求，若存在该标记说明不是内部模块调用
         * 2.判断instanceId是否为空，为空则说明未通过此common组件进行内部调用
         * 3.是否为注册表内的实例id，是否为局域网内ip
         * ...
         */

        // 1.区分请求来源，是否经由gateway
        // 经由gateway转发的请求，需增加"X-Gateway-Proxy"标记，以供此处判断
        boolean isGatewayProxy = "true".equals(request.getHeader(HeaderConstant.X_GATEWAY_PROXY));
        // 当前请求是否有效（目前只要是来源于内部，便认为有效）
        boolean isValidRequest = !isGatewayProxy;
        // 对于模块间接口，都会标记请求来源
        String instanceId = request.getHeader(HeaderConstant.X_INSTANCE_ID);
        log.debug("Origin InstanceId: " + instanceId);

        // 2.判断该请求是否有效
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
