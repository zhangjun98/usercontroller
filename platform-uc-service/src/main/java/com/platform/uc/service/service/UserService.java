package com.platform.uc.service.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.MemberMapper;
import com.platform.uc.service.mapper.RoleMapper;
import com.platform.uc.service.mapper.UserMapper;
import com.platform.uc.service.vo.*;
import com.platform.uc.service.vo.Member;
import com.ztkj.framework.response.core.CommonErrorCode;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业务类
 *
 * @author hao.yan
 */
@Slf4j
@Service
public class UserService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private MemberMapper memberMapper;

	/**
	 * 通过登录信息获取用户信息
	 */
	public UserResponse selectUserByLogin(String searchName){
		QueryMemberRequest request = new QueryMemberRequest();
		request.setSearchName(searchName);
		UserDetail userDetail = userMapper.selectOne(request);
		log.info(JSON.toJSONString(userDetail));
		UserResponse response = BeanUtils.toT(userDetail, UserResponse.class);
		// TODO 根据身份证的过期时间设置 证件是否过期
		// response.setCredentialsExpired();
		// 设置账号是否过期
		if (!ObjectUtils.isEmpty(userDetail.getAccountExpired())){
			Long now = System.currentTimeMillis();
			Long accountExpired = userDetail.getAccountExpired().getTime();
			response.setAccountNonExpired((now.compareTo(accountExpired) > 0));
		}
		// 查询用户角色
		List<Role> roles =  roleMapper.selectByMid(userDetail.getId());
		if (!CollectionUtils.isEmpty(roles)){
			response.setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toSet()));
		}
		return response;
	}

	/**
	 * 通过用户信息编号查询 账户信息 与 用户信息
	 */
	public MemberResponse selectOne(QueryMemberRequest request) {
		MemberDetail detail = memberMapper.selectOne(request);
		if (detail == null){
			throw new BizException(UserErrorCode.USER_NOTFOUND);
		}
		return toMemberReponse(detail);
	}

	private MemberResponse toMemberReponse(MemberDetail detail){
		return BeanUtils.toT(detail, MemberResponse.class);
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
		wrapper.in("mid", ids);
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
