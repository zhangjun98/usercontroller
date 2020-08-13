package com.platform.uc.api;

import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/user")
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
    BizResponse<Void> register(@RequestBody UserRequest request);
    /**
     * 通过用户信息编号查询用户信息
     */
    @GetMapping("/{mid}")
    BizResponse<UserResponse> selectUserByMid(@PathVariable("mid") String mid);

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
     * 修改 用户是否启用与禁用
     */
    @PutMapping("/change/status")
    BizResponse<Void> changeStatus(@RequestBody ChangeStatusRequest request);

}
