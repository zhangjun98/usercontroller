package com.platform.uc.adapter.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 令牌存放
 * @author hao.yan
 */
@Slf4j
@Component
public class BizTokenStore implements TokenStore {

    private static final String ACCESS_TOKEN = "uwo:oauth:access:token:";

    private static final String REFRESH_TOKEN = "uwo:oauth:refresh:token:";

    private static final String AUTHENTICATION_TOKEN = "uwo:oauth:authentication:";

    private static final String CLIENT_TOKEN = "uwo:oauth:client:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    /**
     * 通过accessToken获取Authentication(身份验证)
     * @param accessToken 令牌
     * @return OAuth2Authentication
     */
    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken accessToken) {
        return readAuthentication(accessToken.getValue());
    }

    /**
     * 通过accessToken获取Authentication(身份验证)
     * @param accessToken 令牌
     * @return OAuth2Authentication
     */
    @Override
    public OAuth2Authentication readAuthentication(String accessToken) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(ACCESS_TOKEN + accessToken);
        String authKey = (String) hashOperations.get("authentication");
        if (authKey == null){
            return null;
        }
        BoundHashOperations<String, String, Object> hashOperations1 = this.redisTemplate.boundHashOps(AUTHENTICATION_TOKEN + authKey);
        return (OAuth2Authentication) hashOperations1.get("authentication");
    }



    /**
     * 存储token(令牌)，authentication(身份验证)
     * @param accessToken 令牌
     * @param authentication 身份验证
     */
    @Override
    public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String token = ACCESS_TOKEN + accessToken.getValue();

        // 保存token (meice:oauth:access:token)
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(token);
        hashOperations.put("accessToken", accessToken);

        // 保存authentication (meice:oauth:authentication)
        String authKey = authenticationKeyGenerator.extractKey(authentication);
        hashOperations.put("authentication", authKey);

        BoundHashOperations<String, String, Object> hashOperations1 = this.redisTemplate.boundHashOps(AUTHENTICATION_TOKEN + authKey);
        hashOperations1.put("accessToken", accessToken.getValue());
        hashOperations1.put("authentication", authentication);

        // client记录
        String userName = getUserName(authentication);
        String clientId = authentication.getOAuth2Request().getClientId();
        Collection<String> accessTokens = queryTokensByClientIdAndUserName(clientId, userName);
        BoundHashOperations<String, String, Object> hashOperations2 = this.redisTemplate.boundHashOps(CLIENT_TOKEN + clientId);
        accessTokens.add(accessToken.getValue());
        hashOperations2.put(userName, accessTokens);

        // 有refreshToken就设置refreshToken
        if (accessToken.getRefreshToken() != null && accessToken.getRefreshToken().getValue() != null) {
            hashOperations.put("refreshToken", accessToken.getRefreshToken().getValue());
        }

        // 设置过期时间
        if (accessToken.getExpiration() != null) {
            int seconds = accessToken.getExpiresIn();
            redisTemplate.expire(token, seconds, TimeUnit.SECONDS) ;
            redisTemplate.expire(AUTHENTICATION_TOKEN + authKey, seconds, TimeUnit.SECONDS);
            redisTemplate.expire(CLIENT_TOKEN + clientId, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取用户名
     * @param authentication 授权
     * @return 用户名
     */
    private String getUserName(OAuth2Authentication authentication){
        String userName = "_client";
        if (!authentication.isClientOnly()) {
            // 是用户授权
            userName = authentication.getUserAuthentication().getName();
        }
        return userName;
    }

    /**
     * 读取令牌
     * @param accessToken 令牌
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(ACCESS_TOKEN + accessToken);
        return (OAuth2AccessToken) hashOperations.get("accessToken");
    }

    /**
     * 移除令牌
     * @param accessToken 令牌
     */
    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        OAuth2Authentication authentication = readAuthentication(accessToken.getValue());
        this.redisTemplate.delete(ACCESS_TOKEN + accessToken.getValue());

        String userName = getUserName(authentication);
        String clientId = authentication.getOAuth2Request().getClientId();
        Collection<String> accessTokens = queryTokensByClientIdAndUserName(clientId, userName);
        accessTokens.remove(accessToken.getValue());

        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(CLIENT_TOKEN + clientId);
        hashOperations.put(userName, accessTokens);
    }

    /**
     * 存储refreshToken(刷新令牌)，authentication(身份验证)
     * @param refreshToken 刷新令牌
     * @param authentication 身份验证
     */
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String token = refreshToken.getValue();
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(REFRESH_TOKEN + token);
        hashOperations.put("refreshToken", refreshToken);
        String authKey = authenticationKeyGenerator.extractKey(authentication);
        hashOperations.put("authentication", authKey);
        if (refreshToken instanceof DefaultExpiringOAuth2RefreshToken){
            Date expiration = ((DefaultExpiringOAuth2RefreshToken) refreshToken).getExpiration();
            long expire = (expiration.getTime() - System.currentTimeMillis())/1000;
            hashOperations.expire(expire, TimeUnit.SECONDS);
            this.redisTemplate.expire(AUTHENTICATION_TOKEN + authKey, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 读取刷新令牌
     * @param refreshToken 刷新令牌
     * @return OAuth2RefreshToken
     */
    @Override
    public OAuth2RefreshToken readRefreshToken(String refreshToken) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(REFRESH_TOKEN + refreshToken);
        return (DefaultExpiringOAuth2RefreshToken) hashOperations.get("refreshToken");
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(REFRESH_TOKEN + refreshToken.getValue());
        String authKey = (String) hashOperations.get("authentication");
        if (authKey == null){
            return null;
        }
        BoundHashOperations<String, String, Object> hashOperations1 = this.redisTemplate.boundHashOps(AUTHENTICATION_TOKEN + authKey);
        return (OAuth2Authentication) hashOperations1.get("authentication");
    }

    /**
     * 移除refreshToken
     * @param refreshToken refreshToken
     */
    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        this.redisTemplate.delete(REFRESH_TOKEN + refreshToken.getValue());
    }

    /**
     * 通过refreshToken移除accessToken
     * @param refreshToken refreshToken
     */
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(REFRESH_TOKEN + refreshToken.getValue());
        String authKey = (String) hashOperations.get("authentication");
        if (authKey == null){
            return;
        }
        BoundHashOperations<String, String, Object> hashOperations1 = this.redisTemplate.boundHashOps(AUTHENTICATION_TOKEN + authKey);
        String accessToken = (String) hashOperations1.get("accessToken");
        this.redisTemplate.delete(ACCESS_TOKEN + accessToken);
    }

    /**
     * 通过授权获取token
     * @param authentication 授权
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authKey = authenticationKeyGenerator.extractKey(authentication);
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(AUTHENTICATION_TOKEN + authKey);
        Boolean hasKey = hashOperations.hasKey("accessToken");
        if (hasKey == null || !hasKey){
            return null;
        }
        String accessToken = (String) hashOperations.get("accessToken");
        BoundHashOperations<String, String, Object> hashOperations1 = redisTemplate.boundHashOps(ACCESS_TOKEN + accessToken);
        return (OAuth2AccessToken) hashOperations1.get("accessToken");
    }

    /**
     * 通过clientId与UserName查询token
     * @param clientId clientId
     * @param userName userName
     * @return Collection<OAuth2AccessToken>
     */

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<String> accessTokens = queryTokensByClientIdAndUserName(clientId, userName);
        return accessTokens.stream().map(this::readAccessToken).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Collection<String> queryTokensByClientIdAndUserName(String clientId, String userName) {
        BoundHashOperations<String, String, Object> hashOperations = this.redisTemplate.boundHashOps(CLIENT_TOKEN + clientId);
        Boolean hasKey = hashOperations.hasKey(userName);
        if (hasKey == null || !hasKey){
            return new ArrayList<>();
        }
        return (Collection<String>) hashOperations.get(userName);
    }


    /**
     * 通过clientId查询token
     * @param clientId clientId
     * @return Collection<OAuth2AccessToken>
     */
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return findTokensByClientIdAndUserName(clientId, "_client");
    }
}