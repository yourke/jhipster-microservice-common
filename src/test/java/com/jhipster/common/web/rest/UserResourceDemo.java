package com.jhipster.common.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhipster.common.domain.uaa.SysUserDTO;
import com.jhipster.common.service.uaa.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 模块接口调用示例<br/>
 * 非测试类，仅用于说明在SpringCloud模块中引入common之后，如何调用其它模块接口
 * 
 * @author yuanke
 * @date 2020/7/8 10:54
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户管理Demo")
public class UserResourceDemo {

    @Autowired
    private UserService userService;

    @GetMapping("/users/current")
    @ApiOperation(value = "查询当前用户")
    public ResponseEntity<SysUserDTO> currentUser() {
        SysUserDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    // TODO 文件上传示例
}
