package com.platform.uc.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MemberClientRequest;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.api.vo.response.RoleMemberResponse;
import com.platform.uc.service.service.MemberClientService;
import com.platform.uc.service.vo.MemberClient;

import com.ztkj.framework.response.core.BizPageResponse;

import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台的用户
 * @author fang
 */
@RequestMapping("/member/client")
@RestController
public class MemberClientController {

    @Resource
    private MemberClientService memberClientService;

    /**
     * 查询平台的用户列表
     */
    @PostMapping("/query")
    public BizPageResponse<RoleMemberResponse> selectUsersPage(@RequestBody QueryClientUserRequest request) {
        return memberClientService.selectUsersByClientId(request);
    }

    /**
     * 保存平台用户  用平台与用户关联
     */
    @PostMapping("/")
    @ApiOperation(value = "保存平台用户")
    public BizResponse<Void> save(@RequestBody MemberClientRequest request) {
        memberClientService.save(request);
        return BizResponseUtils.success();
    }

    /**
     * 批量删除平台用户 假删除
     */
    @PostMapping("/change/status")
    @ApiOperation(value = "批量删除或恢复")
    public BizResponse<Void> changeStatus(@RequestBody ChangeStatusRequest request) {
        memberClientService.changeStatus(request);
        return BizResponseUtils.success();
    }

    /**
     * 批量删除平台用户 真删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "批量删除")
    public BizResponse<Void> batchRemove(@RequestBody BatchRequest request) {
        memberClientService.batchDelete(request);
        return BizResponseUtils.success();
    }

}
