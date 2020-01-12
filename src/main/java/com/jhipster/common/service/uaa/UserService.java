
package com.jhipster.common.service.uaa;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.jhipster.common.client.AuthorizedFeignClient;
import com.jhipster.common.client.SpringQueryMap;
import com.jhipster.common.domain.uaa.SysUserDTO;
import com.jhipster.common.domain.uaa.UserDTO;
import com.jhipster.common.domain.uaa.querymap.UserListParam;

/**
 * 模块接口示例
 * 
 * @author yuanke
 * @date 2020/1/12 21:22
 */
@AuthorizedFeignClient(name = "uaa")
public interface UserService {

    /**
     * 获取当前系统用户<br/>
     * （包含是否是租户管理员、租户类别等信息）
     *
     * @return SysUserDTO
     */
    @GetMapping("/api/inner/users/system")
    SysUserDTO getCurrentUser();

    /**
     * 获取指定条件下的用户
     *
     * @param params
     *            动态参数（筛选条件见对象属性）
     * @return 用户对象集合
     */
    @GetMapping("/api/inner/users")
    List<UserDTO> getUsers(@SpringQueryMap UserListParam params);

}
