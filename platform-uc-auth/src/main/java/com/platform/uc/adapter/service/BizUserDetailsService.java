package com.platform.uc.adapter.service;

import com.platform.uc.api.RemoteUserService;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.common.authorization.vo.OAuthGrantedAuthority;
import com.ztkj.framework.common.authorization.vo.OAuthUser;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.core.ErrorCode;
import com.ztkj.framework.response.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户业务类
 * @author hao.yan
 */
@Slf4j
@Service
public class BizUserDetailsService implements UserDetailsService {

    private static final String USER_KEY = "uwo:users:";

    private static final Long USER_TIME = 30 * 60 * 1000L;

    @Resource
    private RedisTemplate<String, UserResponse> redisTemplate;

    @Resource
    private RemoteUserService remoteUserService;

    /**
     * 验证用户密码登录
     */
    @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BoundValueOperations<String, UserResponse> boundValueOps = redisTemplate.boundValueOps(USER_KEY + username);
        Boolean hasKey = redisTemplate.hasKey(USER_KEY + username);
        if (hasKey == null || !hasKey){
            // 把user添加缓存
            BizResponse<UserResponse> response = remoteUserService.selectUserByLogin(username);
            if (response == null){
                throw new UsernameNotFoundException("登录失败");
            }
            if (!ErrorCode.SUCCESS_CODE.equals(response.getCode())){
                throw new UsernameNotFoundException("用户不存在");
            }
            boundValueOps.set(response.getData(), USER_TIME, TimeUnit.MILLISECONDS);
        }
        UserResponse user = boundValueOps.get();
        if (null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        return toUserDetails(user);
    }

    /**
     * 转换对象
     */
    private UserDetails toUserDetails(UserResponse user){
        OAuthUser auth = BeanUtils.toT(user, OAuthUser.class);
        List<OAuthGrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getRoleIds())){
            authorities.addAll(user
                    .getRoleIds().stream()
                    .map(OAuthGrantedAuthority::new)
                    .collect(Collectors.toList())
            );
        }
        auth.setAuthorities(authorities);
        // 账号过期
        auth.setAccountNonExpired(user.isAccountNonExpired());
        // 账号是否锁定
        auth.setAccountNonLocked(!user.isAccountLocked());
        // 证件是否过期
        auth.setCredentialsNonExpired(!user.isCredentialsExpired());
        return auth;
    }

}