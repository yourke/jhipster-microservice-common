package com.jhipster.common.domain.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 创建接口的返回格式
 * 
 * @author yonker
 * @date 2021/7/6 10:26
 */
public class IdDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新记录id值")
    private Integer id;

    public IdDTO() {
        super();
    }

    public IdDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdDTO{" + "id=" + id + '}';
    }
}
