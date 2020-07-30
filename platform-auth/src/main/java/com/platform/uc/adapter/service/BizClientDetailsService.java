package com.platform.uc.adapter.service;

import com.platform.uc.adapter.vo.OAuthClient;
import com.platform.uc.api.RemoteClientService;
import com.platform.uc.api.vo.response.ClientResponse;
import com.ztkj.framework.response.core.BizResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class BizClientDetailsService implements ClientDetailsService {

    private static final String CLIENT_KEY = "uwo:clients:";

    private static final Long CLIENT_TIME = 30 * 60 * 1000L;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RemoteClientService remoteClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps(CLIENT_KEY + clientId);
        Boolean hasKey = redisTemplate.hasKey(CLIENT_KEY + clientId);
        if (hasKey == null || !hasKey){
            // 把clientDetails添加缓存
            BizResponse<ClientResponse> client = remoteClientService.selectClientById(clientId);
            boundValueOps.set(client.getData(), CLIENT_TIME, TimeUnit.MILLISECONDS);
        }
        ClientResponse client =  (ClientResponse)boundValueOps.get();
        if (null == client){
            throw new ClientRegistrationException("平台已禁用此应用");
        }
        return toOAuthClient(client);
    }

    private OAuthClient toOAuthClient(ClientResponse client){
        log.info("client = {}", client);

        OAuthClient authClient = new OAuthClient();
        authClient.setClientId(client.getId());
        authClient.setClientSecret(client.getSecret());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("READ"));
        authorities.add(new SimpleGrantedAuthority("WRITE"));
        authClient.setAuthorities(authorities);

        // 跳转地址
        Set<String> redirectUrls = Arrays.stream(client.getRedirectUrls().split(",")).collect(toSet());
        authClient.setRegisteredRedirectUri(redirectUrls);

        // 授权类型
        Set<String> authorizedGrantTypes = Arrays.stream(client.getGrantTypes().split(",")).collect(toSet());
        authClient.setAuthorizedGrantTypes(authorizedGrantTypes);

        // scope
        Set<String> scope = Arrays.stream(client.getScope().split(",")).collect(toSet());
        scope.add("openid");
        authClient.setScope(scope);
        return authClient;
    }

}
