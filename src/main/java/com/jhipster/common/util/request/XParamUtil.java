package com.jhipster.common.util.request;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求头X-param变量处理工具类<br/>
 * 前端根据国际化语句中是否存在"{}"占位符来判断是否使用x-param变量
 *
 * @author yonker
 * @date 2021/7/8 10:06
 */
public final class XParamUtil {

    private static final Logger log = LoggerFactory.getLogger(XParamUtil.class);

    /** 参数header名 */
    public static final String X_PARAM_HEADER = "X-params";
    /** 多个参数间的分割符 */
    private static final String SEPARATOR = ";;";
    /** 编码集 */
    private static final String CHARSET_UTF = "UTF-8";

    private XParamUtil() {
        // Do Nothing
    }

    /**
     * 获取编码后的变量参数字符串
     * 
     * @param params
     *            变量参数（注意该类型的toString()是否满足需要）
     * @return encoded param
     */
    public static String getParamStr(Object... params) {
        if (params == null || params.length == 0) {
            return null;
        }
        // 拼接参数
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param).append(SEPARATOR);
        }
        // URI编码
        String str = sb.toString();
        try {
            str = URLEncoder.encode(str, CHARSET_UTF);
        } catch (Exception e) {
            log.error("URI参数编码异常: " + str, e);
        }
        return str;
    }

}
