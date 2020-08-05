package com.platform.uc.adapter.handler;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.platform.uc.adapter.contants.AuthorizationContacts;
import com.ztkj.framework.response.utils.EncryptUtils;
import com.ztkj.framework.response.utils.SnowflakeIdUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class BizUserCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public UserDetails selectUserDetails(String token) {
         ValueOperations<String, Object> operations = redisTemplate.opsForValue();
         return (UserDetails) operations.get(token);
    }

    public String saveUserDetails(UserDetails details){
        // 设置1小时后过期
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);
        String token = EncryptUtils.MD5(String.valueOf(snowflakeIdUtils.nextId()));
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(token, details, AuthorizationContacts.LOGIN_EXPIRE, TimeUnit.MILLISECONDS);
        return token;
    }

}
