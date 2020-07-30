package com.platform.uc.adapter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.core.CommonErrorCode;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 授权失败
 * @author hao.yan
 */
@Component
public class CustomOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter printWriter = response.getWriter();
        BizResponse<Void> bizResponse = BizResponseUtils.error(CommonErrorCode.UNAUTHORIZED);
        printWriter.append(objectMapper.writeValueAsString(bizResponse));
    }

}
