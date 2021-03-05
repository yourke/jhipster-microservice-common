package com.jhipster.common.constant;

/**
 * 自定义/常用Header值
 * 
 * @author yonker
 * @date 2021/3/5 11:29
 */
public final class HeaderConstant {

    /** 自定义header值-InstanceId */
    public static final String X_INSTANCE_ID = "InstanceId";

    /** 自定义header值-是否经过gateway代理（需在上游Gateway中标记后以向下游传递） */
    public static final String X_GATEWAY_PROXY = "X-Gateway-Proxy";

    /** 自定义header值-原始请求域名（需在上游Gateway中标记后以向下游传递） */
    public static final String X_ORIGIN_DOMAIN = "X-Origin-Domain";

}
