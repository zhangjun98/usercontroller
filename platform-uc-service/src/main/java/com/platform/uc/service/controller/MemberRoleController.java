package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.request.RoleMemberRequest;
import com.platform.uc.api.vo.response.MemberRoleResponse;
import com.platform.uc.service.service.MemberRoleService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

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
    public BizPageResponse<MemberRoleResponse> selectByConditions(@RequestBody QueryRoleUserRequest request){
        return memberRoleService.selectUsersByConditions(request);
    }

    @PostMapping("/")
    @ApiOperation(value = "保存实体信息")
    public BizResponse<Void> save(@RequestBody Collection<RoleMemberRequest> roleMembers) {
        memberRoleService.save(roleMembers);
        return BizResponseUtils.success();
    }

    @PostMapping("/remove")
    @ApiOperation(value = "批量删除实体信息")
    public BizResponse<Void> remove(@RequestBody BatchRequest request) {
        memberRoleService.remove(request);
        return BizResponseUtils.success();
    }

}
