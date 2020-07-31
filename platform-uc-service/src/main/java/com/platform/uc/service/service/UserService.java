package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.MemberMapper;
import com.platform.uc.service.mapper.UserMapper;
import com.platform.uc.service.utils.BeanCloneUtils;
import com.platform.uc.service.vo.Member;
import com.platform.uc.service.vo.User;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MemberMapper memberMapper;

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
     * 通过用户信息编号查询 账户信息 与 用户信息
     * @param mid
     * @return
     */
    public UserResponse selectByMid(String mid){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(mid)) {
            wrapper.eq("mid", mid);
        }
        User user =  userMapper.selectOne(wrapper);
        return toUserResponse(user);
    }

    private Member selectById(String id){
        return memberMapper.selectById(id);
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
        UserResponse response = BeanCloneUtils.convert(user, UserResponse.class);
        log.info("{}", user);
        long currTime = System.currentTimeMillis();
        Date accountExpired = user.getAccountExpired();
        if (!Objects.isNull(accountExpired)){
            response.setAccountExpired(!(currTime > accountExpired.getTime()));
        }
        Date credentialsExpired = user.getCredentialsExpired();
        if (!Objects.isNull(credentialsExpired)){
            response.setCredentialsExpired(!(currTime > credentialsExpired.getTime()));
        }
        Member member = selectById(user.getMid());
        if (Objects.nonNull(member)){
            response.setMember(BeanCloneUtils.convert(member, MemberResponse.class));
        }
        return response;
    }

}
