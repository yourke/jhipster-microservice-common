package com.jhipster.common.domain.common;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 批量创建接口的返回格式
 * 
 * @author yonker
 * @date 2021/7/6 10:26
 */
public class IdListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新记录id值")
    private List<Integer> id;

    public IdListDTO() {
        super();
    }

    public IdListDTO(List<Integer> id) {
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdDTO{" + "id=" + id + '}';
    }
}
