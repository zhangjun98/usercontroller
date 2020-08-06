package com.platform.uc.adapter.handler;

import com.platform.uc.adapter.contants.AuthorizationContacts;
import com.platform.uc.adapter.vo.LoginResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.exception.BizExceptionUtils;
import com.ztkj.framework.response.utils.BizResponseUtils;
import com.ztkj.framework.response.utils.CookieUtils;
import com.ztkj.framework.response.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权处理
 * @author hao.yan
 */
@Slf4j
@Component
public class BizAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationFailureHandler {

    @Resource
    private BizUserCache userCache;

    @Resource
    private BizRequestCache requestCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);

        log.info("{}", authentication);
        // 保存用户信息 并 获取token
        String token = userCache.saveUserDetails((UserDetails) authentication.getPrincipal());

        // 把登录的token放入cookie中
        CookieUtils.set(response, AuthorizationContacts.LOGIN_TOKEN, token, AuthorizationContacts.LOGIN_EXPIRE.intValue());

//        getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());

        LoginResponse login = new LoginResponse();
        login.setToken(token);
        login.setExpire(AuthorizationContacts.LOGIN_EXPIRE);
        login.setRedirectUri(savedRequest.getRedirectUrl());

        BizResponse<LoginResponse> bizResponse = BizResponseUtils.success(login);
        ResponseUtils.makeSuccessResponse(response, bizResponse);

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        BizResponse<Void> bizResponse = BizExceptionUtils.resolveException(e);
        ResponseUtils.makeFailureResponse(response, bizResponse);
    }
}