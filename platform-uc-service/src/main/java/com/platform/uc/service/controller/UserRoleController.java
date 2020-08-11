package com.platform.uc.service.controller;

import com.platform.uc.service.service.UserRoleService;
import com.platform.uc.service.vo.UserRole;
import com.ztkj.framework.common.domain.Page;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户角色关系控制器
 * @author fang
 */
@RequestMapping("/uc/user_role")
@RestController
public class UserRoleController {

    @Autowired
    @Qualifier("userRoleService")
    private UserRoleService userRoleService;

    @PostMapping
    @ApiOperation(value = "保存实体信息")
    public BizResponse<Integer> saveUserRole(@RequestBody UserRole userRole) {
        return BizResponseUtils.success(userRoleService.saveUserRole(userRole));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<UserRole> findById(@PathVariable String id) {
        return BizResponseUtils.success(userRoleService.selectById(id));
    }

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    public BizResponse<Integer> modify(@RequestBody UserRole userRole) {
        return BizResponseUtils.success(userRoleService.updateUserRole(userRole));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    public BizResponse<Integer> removeById(@PathVariable String id) {
        return BizResponseUtils.success(userRoleService.deleteById(id));
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    public BizResponse<Integer> batchRemove(@RequestBody List<String> idList) {
        return BizResponseUtils.success(userRoleService.batchDelete(idList));
    }
}
