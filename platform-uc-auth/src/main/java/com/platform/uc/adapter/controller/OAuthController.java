package com.platform.uc.adapter.controller;

import com.platform.uc.adapter.vo.Scope;
import com.platform.uc.adapter.vo.TokenResponse;
import com.platform.uc.api.RemoteClientService;
import com.platform.uc.api.RemtoeScopeService;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.api.vo.response.ScopeResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.core.CommonErrorCode;
import com.ztkj.framework.response.utils.BizResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.MapUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 授权控制器
 * @author hao.yan
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
@RequestMapping("/oauth")
public class OAuthController {
    /**
     * 调用 spring的认证方式
     */
    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private RemoteClientService remoteClientService;

    @Resource
    private RemtoeScopeService remtoeScopeService;

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
        String clientId = authorizationRequest.getClientId();
        // 获取客户端信息
        BizResponse<ClientResponse> clientResponse =  remoteClientService.selectClientById(clientId);
        if (!clientResponse.getCode().equals(CommonErrorCode.SUCCESS_CODE) || (clientResponse.getData() == null)){
            model.put("userOauthApproval", false);
            return "grant";
        }
        model.put("client", clientResponse.getData().getAdditionalInformation());

        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
                model.get("scopes") : request.getAttribute("scopes"));
        if (MapUtils.isEmpty(scopes)){
            model.put("userOauthApproval", false);
            return "grant";
        }

        Map<String, String> searchScopes = scopes.entrySet()
                .stream()
                .collect(Collectors.toMap(item->item.getKey().substring(item.getKey().indexOf(".") + 1), Map.Entry::getValue));
        BizPageResponse<ScopeResponse> pageResponse = remtoeScopeService.searchByScopeNames(searchScopes.keySet());
        if (!pageResponse.getCode().equals(CommonErrorCode.SUCCESS_CODE)){
            model.put("userOauthApproval", false);
            return "grant";
        }
        Collection<ScopeResponse> _scopes = pageResponse.getDatas();
        if (CollectionUtils.isEmpty(_scopes)){
            model.put("userOauthApproval", false);
            return "grant";
        }
        List<Scope> scopeList = _scopes.stream().map(item->{
            Boolean checked = Boolean.parseBoolean(searchScopes.get(item.getName()));
            return this.toScope(item, checked);
        }).collect(toList());
        model.put("userOauthApproval", true);
        model.put("userScopes", scopeList);
        return "grant";
    }


    private Scope toScope(ScopeResponse response, Boolean checked){
        // 获取返回申请的范围信息
        Scope scope = new Scope();
        scope.setName(response.getName());
        scope.setChecked((!checked)?true:checked);
        scope.setDescribe(response.getDescribe());
        return scope;
    }

    /**
     * 确认授权页
     * 只有authorization_code授权才有
     */
    @RequestMapping("/error")
    public String error(
            Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String clientId = request.getParameter("client_id");
        // 获取客户端信息
        BizResponse<ClientResponse> clientResponse =  remoteClientService.selectClientById(clientId);
        if (!clientResponse.getCode().equals(CommonErrorCode.SUCCESS_CODE) || (clientResponse.getData() == null)){
            return "grant_error";
        }
        model.put("client", clientResponse.getData().getAdditionalInformation());
        return "grant_error";
    }

}