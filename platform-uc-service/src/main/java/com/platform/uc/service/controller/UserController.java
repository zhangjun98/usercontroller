package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.LoginUserRequest;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.service.UserService;
import com.platform.uc.service.vo.User;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
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
    public BizResponse<Void> register(@RequestBody UserRequest request){
        userService.register(request);
        return BizResponseUtils.success();
    }

}
