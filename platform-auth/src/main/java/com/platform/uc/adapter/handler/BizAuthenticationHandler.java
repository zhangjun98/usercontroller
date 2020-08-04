package com.platform.uc.adapter.handler;

import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 授权处理
 * @author hao.yan
 */
@Slf4j
@Component
public class BizAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationFailureHandler {

    @Resource
    private BizRequestCache requestCache;

//    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        super.onAuthenticationSuccess(request, response, authentication);
//        return;

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object attribute = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            log.info("跳转到登录页的地址为: {}", attribute);
        }
        if (ResponseUtils.isAjaxRequest(request)) {
            BizResponse<String> data = new BizResponse<>();
            data.setMessage("请通过授权码模式跳转到该页面");
            if (savedRequest == null) {
                ResponseUtils.makeFailureResponse(response, data);
                return;
            }
            data.setData(savedRequest.getRedirectUrl());
            ResponseUtils.makeSuccessResponse(response, data);
        } else {
            if (savedRequest == null) {
                super.onAuthenticationSuccess(request, response, authentication);
                return;
            }
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String message;
        if (e instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (e instanceof LockedException) {
            message = "用户已被锁定！";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        BizResponse<Void> febsResponse = new BizResponse<>();
        febsResponse.setMessage(message);
        ResponseUtils.makeFailureResponse(response, febsResponse);
    }
}