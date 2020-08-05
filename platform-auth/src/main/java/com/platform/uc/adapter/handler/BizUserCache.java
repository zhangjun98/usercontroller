package com.platform.uc.adapter.handler;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class BizUserCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public UserDetails getUserFromCache(String token) {
         ValueOperations<String, Object> operations = redisTemplate.opsForValue();
         return (UserDetails) operations.get(token);
    }

    public String saveUserInCache(UserDetails details){
        //String salt = BCrypt.gensalt();
        //将盐保存在redis中
        String salt = "123456ef";
        Algorithm algorithm = Algorithm.HMAC256(salt);
        //设置1小时后过期
        Date date = new Date(System.currentTimeMillis() + 3600 * 1000);
        String token = JWT.create()
                .withSubject(JSON.toJSONString(details))
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(token, details);
        return token;
    }

    public void removeUserFromCache(String token) {
        redisTemplate.delete(token);
    }

}
