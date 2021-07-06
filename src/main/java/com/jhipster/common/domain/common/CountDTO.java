package com.jhipster.common.domain.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 修改、删除接口的返回格式
 * 
 * @author yonker
 * @date 2021/7/6 10:26
 */
public class CountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "影响记录数")
    private Integer count;

    public CountDTO() {
        super();
    }

    public CountDTO(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountDTO{" + "count=" + count + '}';
    }
}
