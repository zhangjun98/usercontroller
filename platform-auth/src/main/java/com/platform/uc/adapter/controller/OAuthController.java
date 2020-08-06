package com.platform.uc.adapter.controller;

import com.platform.uc.adapter.vo.TokenResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

/**
 * 授权控制器
 * @author hao.yan
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    /**
     * 授权请求
     */
    @GetMapping("/token")
    @ResponseBody
    public BizResponse<TokenResponse> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return postAccessToken(principal, parameters);
    }

    /**
     * 授权请求
     */
    @PostMapping("/token")
    @ResponseBody
    public BizResponse<TokenResponse> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    /**
     * 定制申请TOKEN返回实体
     */
    private BizResponse<TokenResponse> custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        TokenResponse response = new TokenResponse();
        response.setAccessToken(token.getValue());
        response.setAccessExpire(token.getExpiresIn());
        if (token.getRefreshToken() != null) {
            response.setRefreshToken(token.getRefreshToken().getValue());
            if (token.getRefreshToken() instanceof ExpiringOAuth2RefreshToken){
                ExpiringOAuth2RefreshToken refreshToken = (ExpiringOAuth2RefreshToken) token.getRefreshToken();
                Date expiration = refreshToken.getExpiration();
                int expiresIn = expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                        .intValue() : 0;
                response.setRefreshExpire(expiresIn);
            }
        }
        response.setType(accessToken.getTokenType());
        return BizResponseUtils.success(response);
    }

    /**
     * 确认授权页
     * 只有authorization_code授权才有
     */
    @RequestMapping("/confirm/access")
    public String confirm_access(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
                model.get("scopes") : request.getAttribute("scopes"));
        log.info("{}", scopes);

        return "grant";
    }

}