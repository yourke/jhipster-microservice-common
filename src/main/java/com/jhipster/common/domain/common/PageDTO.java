package com.jhipster.common.domain.common;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页查询接口的返回格式
 * 
 * @author yonker
 * @date 2021/7/6 10:26
 */
public class PageDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对象集合")
    private List<T> items;

    @ApiModelProperty(value = "总数")
    private Integer total;

    public PageDTO() {
        super();
    }

    public PageDTO(List<T> items, Integer total) {
        this.items = items;
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageDTO{" + "items=" + items + ", total=" + total + '}';
    }
}
