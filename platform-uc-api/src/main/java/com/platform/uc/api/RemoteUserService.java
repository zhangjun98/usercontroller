package com.platform.uc.api;

import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.ForgotPasswordRequest;
import com.platform.uc.api.vo.request.ResetPasswordRequest;
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
@FeignClient(value = "platform-uc-service", path = "/uc/user")
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     */
    @PostMapping("/account")
    BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName);

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
