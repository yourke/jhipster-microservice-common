package com.jhipster.common.util.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jhipster.common.constant.HeaderConstant;

/**
 * 项目工具类
 * 
 * @author yonker
 * @date 2021/3/5 12:37
 */
public class ProjectUtil {

    /**
     * 获取当前项目路径
     *
     * @return 当前项目路径，如http://demo.jhipster.com:80
     */
    public static String getProjectAddress() {
        // 获取Request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return getProjectAddress(requestAttributes.getRequest());
    }

    /**
     * 获取当前项目路径
     *
     * @return 当前项目路径，如http://demo.jhipster.com:80
     */
    public static String getProjectAddress(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // gateway中标记的原始请求url
        String domain = request.getHeader(HeaderConstant.X_ORIGIN_DOMAIN);
        if (domain == null) {
            domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }
        return domain;
    }
}