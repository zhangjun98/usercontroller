package com.platform.uc.service.controller;

import com.platform.uc.service.service.MemberClientService;
import com.platform.uc.service.vo.MemberClient;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户平台关系控制器
 * @author fang
 */
@RequestMapping("/uc/member_client")
@RestController
public class MemberClientController {

    @Resource
    private MemberClientService memberClientService;

    @PostMapping
    @ApiOperation(value = "保存实体信息")
    public BizResponse<Integer> saveMemberClient(@RequestBody MemberClient memberClient) {
        return BizResponseUtils.success(memberClientService.saveMemberClient(memberClient));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<MemberClient> findById(@PathVariable String id) {
        return BizResponseUtils.success(memberClientService.selectById(id));
    }

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    public BizResponse<Integer> modify(@RequestBody MemberClient memberClient) {
        return BizResponseUtils.success(memberClientService.updateUserRole(memberClient));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    public BizResponse<Integer> removeById(@PathVariable String id) {
        return BizResponseUtils.success(memberClientService.deleteById(id));
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    public BizResponse<Integer> batchRemove(@RequestBody List<String> idList) {
        return BizResponseUtils.success(memberClientService.batchDelete(idList));
    }
}
