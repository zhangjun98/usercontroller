package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.service.UserService;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器
 * @author hao.yan
 */
@RequestMapping("/uc/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录信息查询
     */
    @PostMapping("/account")
    public BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName){
        UserResponse user = userService.selectUserByLogin(accountName);
        return BizResponseUtils.success(user);
    }

    /**
     * 注册用户信息
     */
    @PostMapping("/register")
    public BizResponse<Void> register(@RequestBody RegisterUserRequest request){
        userService.register(request);
        return BizResponseUtils.success();
    }

    /**
     * 通过mid获取用户信息与账户信息
     */
    @GetMapping("/{mid}")
    public BizResponse<UserResponse> selectUserByMid(@PathVariable("mid") String mid){
        return BizResponseUtils.success(userService.selectByMid(mid));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<UserResponse> findById(@PathVariable String id) {
        return BizResponseUtils.success(userService.selectById(id));
    }

    @GetMapping("/isExist")
    @ApiOperation(value = "检验是否存在")
    public BizResponse<Boolean> isExist(@RequestParam("accountName") String accountName) {
        return BizResponseUtils.success((userService.selectUserByLogin(accountName) != null));
    }

    @PutMapping("/")
    @ApiOperation(value = "修改用户信息")
    public BizResponse<Void> modify(@RequestBody UpdateMemberRequest request) {
        userService.update(request);
        return BizResponseUtils.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("/reset/password")
    @ApiOperation("重置密码")
    public BizResponse<Void> resetPassword(@RequestBody ResetPasswordRequest password) {
        userService.resetPassword(password);
        return BizResponseUtils.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/forgot/password")
    @ApiOperation("修改密码")
    public BizResponse<Void> changePassword(@RequestBody ForgotPasswordRequest password) {
        userService.changePassword(password);
        return BizResponseUtils.success();
    }

    /**
     * 修改 用户是否启用与禁用
     */
    @PutMapping("/change/status")
    public BizResponse<Void> changeStatus(@RequestBody ChangeStatusRequest request){
        userService.setEnableOrDisable(request.isEnable(), request.getUserIds());
        return BizResponseUtils.success();
    }


}
