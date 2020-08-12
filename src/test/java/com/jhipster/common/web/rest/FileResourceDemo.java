package com.jhipster.common.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jhipster.common.domain.base.FileMeta;
import com.jhipster.common.service.base.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 模块接口调用示例<br/>
 * 非测试类，仅用于说明在SpringCloud模块中引入common之后，如何调用其它模块接口
 * 
 * @author yuanke
 * @date 2020/7/8 10:54
 */
@RestController
@RequestMapping("/api")
@Api(tags = "文件管理Demo")
public class FileResourceDemo {

    @Autowired
    private BaseService baseService;

    @PostMapping("/files/upload")
    @ApiOperation(value = "文件上传")
    public ResponseEntity<FileMeta> uploadFile(
            @ApiParam(value = "文件", required = true) @RequestParam(value = "file") MultipartFile file,
            @ApiParam(value = "指定文件名") @RequestParam(value = "name", required = false) String name) {
        FileMeta fileMeta = baseService.uploadFile(file, name);
        return ResponseEntity.ok(fileMeta);
    }
}
