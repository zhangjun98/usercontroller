package com.platform.uc.adapter.controller;

import com.platform.uc.adapter.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * 用户控制
 * @author hao.yan
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public BizResponse<UserResponse> info(Principal principal){
        log.info("{}", SecurityContextHolder.getContext());
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
        return null;
    }

}
