package com.platform.uc.adapter.service;

import com.platform.uc.adapter.vo.OAuthClient;
import com.platform.uc.api.RemoteClientService;
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.core.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toSet;

/**
 * client service
 * @author hao.yan
 */
@Slf4j
@Service
public class BizClientDetailsService implements ClientDetailsService {

    private static final String CLIENT_KEY = "uwo:clients:";

    private static final Long CLIENT_TIME = 30 * 60 * 1000L;

    private static final Long NOTFOUND_CLIENT_TIME = 5 * 60 * 1000L;

    @Resource
    private PasswordEncoder passwordEncoder;

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
            BizResponse<ClientResponse> response = remoteClientService.selectClientById(clientId);
            if (Objects.nonNull(response) && response.getCode().equals(CommonErrorCode.SUCCESS_CODE)){
                long time = CLIENT_TIME;
                if (Objects.isNull(response.getData())){
                    time = NOTFOUND_CLIENT_TIME;
                }
                boundValueOps.set(response.getData(), time, TimeUnit.MILLISECONDS);
            }

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

        authClient.setAdditionalInformation(client.getAdditionalInformation());

        // 设置是否开启自动授权
        if (!StringUtils.isEmpty(client.getAutoApproveScopes())){
            Set<String> autoApproveScopes = Arrays.stream(client.getAutoApproveScopes().split(",")).collect(toSet());
            authClient.setAutoApproveScopes(autoApproveScopes);
        }

        // scope
        if (!StringUtils.isEmpty(client.getScope())) {
            authClient.setScope(Arrays.stream(client.getScope().split(",")).collect(toSet()));
        }
        return authClient;
    }

    /**
     * 保存client信息
     */
    public void saveClientDetails(){
        ClientRequest client = new ClientRequest();
        client.setId("");
        client.setSecret(passwordEncoder.encode(""));
        remoteClientService.save(client);
    }


}
