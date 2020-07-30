package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.UserMapper;
import com.platform.uc.service.vo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户业务类
 * @author hao.yan
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 通过登录信息获取用户信息
     */
    public UserResponse selectUserByLogin(String accountName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(accountName)) {
            wrapper.eq("username", accountName)
                    .or()
                    .eq("email", accountName)
                    .or()
                    .eq("mobile", accountName);
        }
        User user =  userMapper.selectOne(wrapper);
        return toUserResponse(user);
    }

    /**
     * 注册用户
     */
    public void register(UserRequest request){
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }

    private UserResponse toUserResponse(User user){
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        long currTime = System.currentTimeMillis();
        Date accountExpired = user.getAccountExpired();
        if (!Objects.isNull(accountExpired)){
            response.setAccountExpired(!(currTime > accountExpired.getTime()));
        }
        Date credentialsExpired = user.getCredentialsExpired();
        if (!Objects.isNull(credentialsExpired)){
            response.setCredentialsExpired(!(currTime > credentialsExpired.getTime()));
        }
        return response;
    }

}
