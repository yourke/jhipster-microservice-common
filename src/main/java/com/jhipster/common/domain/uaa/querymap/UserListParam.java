package com.jhipster.common.domain.uaa.querymap;

/**
 * 用户对象接口 动态查询参数<br/>
 * 
 * @author yonker
 * @date 2019/10/12 10:30
 */
public class UserListParam {

    /** 组织ids */
    private String orgIds;

    /** 租户ids */
    private String tenantIds;

    public String getOrgIds() {
        return orgIds;
    }

    public UserListParam setOrgIds(String orgIds) {
        this.orgIds = orgIds;
        return this;
    }

    public String getTenantIds() {
        return tenantIds;
    }

    public UserListParam setTenantIds(String tenantIds) {
        this.tenantIds = tenantIds;
        return this;
    }

}
