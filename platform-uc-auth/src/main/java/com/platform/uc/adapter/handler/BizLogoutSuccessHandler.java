package com.platform.uc.adapter.handler;

import com.platform.uc.adapter.vo.LogoutResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import com.ztkj.framework.response.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class BizLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private TokenStore tokenStore;

    @Value("${zt.default.logout.redirect.url:}")
    private String redirectUrl;
    /**
     * 分离出请求中包含的token的一个分离器
     */
    private final BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

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

        // 返回登录信息
        LogoutResponse logoutResponse = new LogoutResponse(determineTargetUrl(request));
        ResponseUtils.makeSuccessResponse(response, BizResponseUtils.success(logoutResponse));
    }

    protected String determineTargetUrl(HttpServletRequest request) {
        if (!StringUtils.isEmpty(redirectUrl)) {
            return redirectUrl;
        }

        return request.getHeader("Referer");
    }

}