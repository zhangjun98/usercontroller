package com.platform.uc.api;

import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.request.RoleMemberRequest;
import com.platform.uc.api.vo.response.RoleMemberResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 远程用户角色信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/member/role")
public interface RemoteMemberRoleService {

    /**
     * 查询角色下的用户
     */
    @PostMapping("/users")
    BizPageResponse<RoleMemberResponse> selectByConditions(@RequestBody QueryRoleUserRequest request);

    @PostMapping("/")
    BizResponse<Void> save(@RequestBody Collection<RoleMemberRequest> roleMembers);

    @PostMapping("/remove")
    BizResponse<Void> remove(@RequestBody BatchRequest request);

    /**
     * 删除用户下面的角色
     */
    @DeleteMapping("/{mid}/remove")
    BizResponse<Void> remove(@PathVariable("mid")String mid);

}
