package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.MemberMapper;
import com.platform.uc.service.mapper.UserMapper;
import com.platform.uc.service.utils.BeanCloneUtils;
import com.platform.uc.service.vo.*;
import com.platform.uc.service.vo.Member;
import com.ztkj.framework.response.core.CommonErrorCode;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户业务类
 *
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
		if (!StringUtils.isEmpty(accountName)){
			wrapper
					.eq("username", accountName)
				.or()
					.eq("email", accountName)
				.or()
					.eq("mobile", accountName);
		}
		User user = userMapper.selectOne(wrapper);
		Member member = selectByMemberId(user.getMid());
		return toUserResponse(user, member);
	}

	/**
	 * 通过用户信息编号查询 账户信息 与 用户信息
	 */
	public UserResponse selectByMid(String mid) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(mid)) {
			wrapper.eq("mid", mid);
		}
		User user = userMapper.selectOne(wrapper);
		Member member = selectByMemberId(mid);
		return toUserResponse(user, member);
	}

	public UserResponse selectById(String id) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(id)) {
			wrapper.eq("id", id);
		}
		User user = userMapper.selectOne(wrapper);
		Member member = selectByMemberId(user.getMid());
		return toUserResponse(user, member);
	}

	private Member selectByMemberId(String id) {
		return memberMapper.selectById(id);
	}

	/**
	 * 注册用户
	 */
	public String register(RegisterUserRequest request) {
		// 保存用户信息 获取到MID
		Member member = BeanUtils.toT(request.getMember(), Member.class);
		memberMapper.insert(member);
		// 保存账户信息
		User user = BeanUtils.toT(request, User.class);
		user.setMid(member.getId());
		user.setCreateTime(new Date());
		userMapper.insert(user);
		return member.getId();
	}

	private UserResponse toUserResponse(User user, Member member) {
		UserResponse response = BeanCloneUtils.convert(user, UserResponse.class);
		log.info("{}", user);
		long currTime = System.currentTimeMillis();
		Date accountExpired = user.getAccountExpired();
		if (!Objects.isNull(accountExpired)) {
			response.setAccountExpired(!(currTime > accountExpired.getTime()));
		}
		Date credentialsExpired = user.getCredentialsExpired();
		if (!Objects.isNull(credentialsExpired)) {
			response.setCredentialsExpired(!(currTime > credentialsExpired.getTime()));
		}
		if (Objects.nonNull(member)) {
			response.setMember(BeanCloneUtils.convert(member, MemberResponse.class));
		}
		return response;
	}

	/**
	 * 重置密码
	 */
	public void resetPassword(ResetPasswordRequest request) {
		if (!StringUtils.equals(request.getNewPassword(), request.getRepeatPassword())) {
			throw new BizException(UserErrorCode.PASSWORD_INCONSISTENCY);
		}
		User user = new User();
		user.setId(request.getId());
		user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
		userMapper.updateById(user);
	}

	/**
	 * 修改密码
	 */
	public void changePassword(ForgotPasswordRequest request) {
		// 校验旧密码
		User user = userMapper.selectById(request.getId());
		if (!new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
			throw new BizException(CommonErrorCode.BAD_CREDENTIALS);
		}
		resetPassword(request);
	}

	/**
	 * 启用用户
	 */
	public void setEnableOrDisable(boolean isEnable, Set<String> ids) {
		User user = new User();
		user.setEnabled(isEnable);
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.in("id", ids);
		userMapper.update(user, wrapper);
	}

	/**
	 * 设置头像
	 */
	public void update(UpdateMemberRequest request) {
		Member member = BeanUtils.toT(request, Member.class);
		int code = memberMapper.updateById(member);
		if(code <= 0){
			throw new BizException(UserErrorCode.MEMBER_UPDATE_FAIL);
		}
	}

}
