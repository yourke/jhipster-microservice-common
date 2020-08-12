package com.jhipster.common.domain.base;

import io.swagger.annotations.ApiModel;

/**
 * 文件元信息 DTO
 * 
 * @author yuenke
 * @date 2020/1/12 21:32
 */
@ApiModel
public class FileMeta {

    /** 文件名称 */
    private String name;

    /** 文件大小 */
    private Long size;

    /** 相对路径 */
    private String path;

    /** 全路径 */
    private String fullPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
