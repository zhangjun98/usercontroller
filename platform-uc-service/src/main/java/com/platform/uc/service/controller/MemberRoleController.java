package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.response.RoleMemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.service.MemberRoleService;
import com.platform.uc.service.vo.RoleMember;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色关系控制器
 * @author fang
 */
@RequestMapping("/member/role")
@RestController
public class MemberRoleController {

    @Resource
    private MemberRoleService memberRoleService;

    /**
     * 查询角色下的用户
     */
    @PostMapping("/users")
    public BizPageResponse<RoleMemberResponse> selectByConditions(@RequestBody QueryRoleUserRequest request){
        return memberRoleService.selectUsersByConditions(request);
    }


    @PostMapping
    @ApiOperation(value = "保存实体信息")
    public BizResponse<Integer> saveMemberRole(@RequestBody RoleMember roleMember) {
        return BizResponseUtils.success(memberRoleService.saveMemberRole(roleMember));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<RoleMember> findById(@PathVariable String id) {
        return BizResponseUtils.success(memberRoleService.selectById(id));
    }

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    public BizResponse<Integer> modify(@RequestBody RoleMember roleMember) {
        return BizResponseUtils.success(memberRoleService.updateUserRole(roleMember));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    public BizResponse<Integer> removeById(@PathVariable String id) {
        return BizResponseUtils.success(memberRoleService.deleteById(id));
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    public BizResponse<Integer> batchRemove(@RequestBody List<String> idList) {
        return BizResponseUtils.success(memberRoleService.batchDelete(idList));
    }
}
