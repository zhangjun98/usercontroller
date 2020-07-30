package com.platform.uc.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 授权代码
 * @author hao.yan
 */
@Slf4j
@Component
public class AuthorizationCodeService extends RandomValueAuthorizationCodeServices {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void store(String code, OAuth2Authentication oAuth2Authentication) {
        BoundValueOperations<String, Object> boundValueOperations = redisTemplate.boundValueOps(code);
        boundValueOperations.set(oAuth2Authentication);
        boundValueOperations.expire(60 * 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication = (OAuth2Authentication) redisTemplate.boundValueOps(code).get();
        if (null != authentication) {
            redisTemplate.delete(code);
        }
        return authentication;
    }

}