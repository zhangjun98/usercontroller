package com.platform.uc.api;

import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/user")
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     */
    @PostMapping("/account")
    BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName);

    /**
     * 注册用户信息
     */
    @PostMapping("/register")
    BizResponse<String> register(@RequestBody RegisterUserRequest request);

    /**
     * 通过用户信息编号查询用户信息
     */
    @GetMapping("/{mid}")
    BizResponse<MemberResponse> selectUserByMid(@PathVariable("mid") String mid);

    /**
     * 修改用户信息
     */
    @PutMapping("/")
    BizResponse<Void> modify(@RequestBody UpdateMemberRequest request);

    /**
     * 设置密码
     */
    @PutMapping("/reset/password")
    BizResponse<Void> resetPassword(@RequestBody ResetPasswordRequest password);

    /**
     * 修改密码
     */
    @PutMapping("/forgot/password")
    BizResponse<Void> changePassword(@RequestBody ForgotPasswordRequest password);

    /**
     * 检验账户是否存在
     */
    @GetMapping("/isExist")
    @ApiOperation(value = "检验是否存在")
    BizResponse<Boolean> isExist(@RequestParam("accountName") String accountName);

    /**
     * 修改 用户是否启用与禁用
     */
    @PutMapping("/change/status")
    BizResponse<Void> changeStatus(@RequestBody ChangeStatusRequest request);

    @PutMapping("/configureRoles/{id}")
    BizResponse<Void> configureRoles(@PathVariable String id , @RequestBody List<String> ids);

    @PutMapping("/configureClients/{id}")
    BizResponse<Void> configureClients(@PathVariable String id , @RequestBody List<String> ids);
}
