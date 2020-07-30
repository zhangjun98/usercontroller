package com.platform.uc.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出
 * @author hao.yan
 */
@Slf4j
@Component
public class BizLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Resource
    private TokenStore tokenStore;


    private final BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();


    public BizLogoutSuccessHandler() {
        // 重定向到原地址
        this.setUseReferer(true);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            // 解密请求头
            authentication = tokenExtractor.extract(request);
            if(authentication != null && authentication.getPrincipal() != null){
                String tokenValue = authentication.getPrincipal().toString();
                log.debug("revokeToken tokenValue:{}", tokenValue);
                // 移除token
                tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
            }
        }catch (Exception e){
            log.error("revokeToken error: ",e);
        }
        super.onLogoutSuccess(request, response, authentication);
    }
}