package com.platform.uc.service.controller;

import com.platform.uc.service.service.MemberRoleService;
import com.platform.uc.service.vo.MemberRole;
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
@RequestMapping("/uc/member_role")
@RestController
public class MemberRoleController {

    @Resource
    private MemberRoleService memberRoleService;

    @PostMapping
    @ApiOperation(value = "保存实体信息")
    public BizResponse<Integer> saveMemberRole(@RequestBody MemberRole memberRole) {
        return BizResponseUtils.success(memberRoleService.saveMemberRole(memberRole));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<MemberRole> findById(@PathVariable String id) {
        return BizResponseUtils.success(memberRoleService.selectById(id));
    }

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    public BizResponse<Integer> modify(@RequestBody MemberRole memberRole) {
        return BizResponseUtils.success(memberRoleService.updateUserRole(memberRole));
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
