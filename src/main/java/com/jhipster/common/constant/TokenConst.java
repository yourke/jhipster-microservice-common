package com.jhipster.common.constant;

/**
 * 自定义token属性常量<br/>
 * token属性取决于在uaa中签发token时所定义的，建议只加入必要且固定的属性，毕竟token长度是有限制的
 *
 * @author yuenke
 * @date 2019/10/10 19:04
 */
public final class TokenConst {

    /** 用户id */
    public static final String USER_ID = "uid";

    /** 租户id */
    public static final String TENANT_ID = "tid";

}
