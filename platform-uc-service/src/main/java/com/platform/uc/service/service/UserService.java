package com.platform.uc.service.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.PasswordVo;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.MemberClientMapper;
import com.platform.uc.service.mapper.MemberMapper;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.mapper.UserMapper;
import com.platform.uc.service.utils.BeanCloneUtils;
import com.platform.uc.service.vo.*;
import com.ztkj.framework.common.domain.CodeMessage;
import com.ztkj.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户业务类
 *
 * @author hao.yan
 */
@Slf4j @Service public class UserService
{

	@Resource private UserMapper userMapper;

	@Resource private MemberMapper memberMapper;

	@Resource private MemberRoleMapper memberRoleMapper;

	@Resource private MemberClientMapper memberClientMapper;

	/**
	 * 通过登录信息获取用户信息
	 */
	public UserResponse selectUserByLogin(String accountName)
	{
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(accountName))
		{
			wrapper.eq("username", accountName).or().eq("email", accountName).or().eq("mobile", accountName);
		}
		User user = userMapper.selectOne(wrapper);
		return toUserResponse(user);
	}

	/**
	 * 通过用户信息编号查询 账户信息 与 用户信息
	 *
	 * @param mid
	 * @return
	 */
	public UserResponse selectByMid(String mid)
	{
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(mid))
		{
			wrapper.eq("mid", mid);
		}
		User user = userMapper.selectOne(wrapper);
		return toUserResponse(user);
	}

	public UserResponse selectById(String id)
	{
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(id))
		{
			wrapper.eq("id", id);
		}
		User user = userMapper.selectOne(wrapper);
		user.setMember(this.selectByMemberId(id));
		return toUserResponse(user);
	}

	public Member selectByMemberId(String id)
	{
		return memberMapper.selectById(id);
	}

	/**
	 * 注册用户
	 */
	public void register(UserRequest request)
	{
		User user = new User();
		BeanUtils.copyProperties(request, user);
		user.setCreateTime(new Date());
		user.setIsDelete(0);
		userMapper.insert(user);
	}

	private UserResponse toUserResponse(User user)
	{
		UserResponse response = BeanCloneUtils.convert(user, UserResponse.class);
		log.info("{}", user);
		long currTime = System.currentTimeMillis();
		Date accountExpired = user.getAccountExpired();
		if (!Objects.isNull(accountExpired))
		{
			response.setAccountExpired(!(currTime > accountExpired.getTime()));
		}
		Date credentialsExpired = user.getCredentialsExpired();
		if (!Objects.isNull(credentialsExpired))
		{
			response.setCredentialsExpired(!(currTime > credentialsExpired.getTime()));
		}
		Member member = selectByMemberId(user.getMid());
		if (Objects.nonNull(member))
		{
			response.setMember(BeanCloneUtils.convert(member, MemberResponse.class));
		}
		return response;
	}

	/**
	 * 重置密码
	 *
	 * @param id
	 * @param paramsMap
	 */
	public void resetPassword(String id, Map<String, String> paramsMap)
	{
		String password = paramsMap.get("password");
		String repeatPassword = paramsMap.get("repeatPassword");
		if (!org.apache.commons.lang.StringUtils.equals(password, repeatPassword))
		{
			throw new ServiceException(new CodeMessage(500, "密码输入不一致！"));
		}
		//加密
		password = new BCryptPasswordEncoder().encode(password);
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(id))
		{
			wrapper.eq("id", id);
		}
		User temp = new User();
		temp.setId(id);
		temp.setPassword(password);
		userMapper.update(temp, wrapper);
	}

	/**
	 * 修改密码
	 *
	 * @param passwordVo
	 * @param passwordVo
	 */
	public void changePassword(PasswordVo passwordVo)
	{
		//校验旧密码
		User userOfDatabase = userMapper.selectById(passwordVo.getId());
		if (!new BCryptPasswordEncoder().matches(passwordVo.getOldPassword(), userOfDatabase.getPassword()))
		{
			throw new ServiceException(new CodeMessage(500, "密码错误!"));
		}
		//新密码校验
		if (!StrUtil.equals(passwordVo.getNewPassword(), passwordVo.getRepeatPassword()))
		{
			throw new ServiceException(new CodeMessage(500, "密码输入不一致！"));
		}
		//重设密码
		userOfDatabase.setPassword(new BCryptPasswordEncoder().encode(passwordVo.getNewPassword()));
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(passwordVo.getId()))
		{
			wrapper.eq("id", passwordVo.getId());
		}
		userMapper.update(userOfDatabase, wrapper);
	}

	/**
	 * 启用用户
	 *
	 * @param ids
	 */
	public void enableUser(List<String> ids)
	{
		for (String id : ids)
		{
			QueryWrapper<User> wrapper = new QueryWrapper<>();
			if (!StringUtils.isEmpty(id))
			{
				wrapper.eq("enabled", 1);
			}
			User userOfDatabase = userMapper.selectById(id);
			if (userOfDatabase != null)
			{
				userMapper.update(userOfDatabase, wrapper);
			}
		}
	}

	/**
	 * 停用用户
	 *
	 * @param ids
	 */
	public void disableUser(List<String> ids)
	{
		for (String id : ids)
		{
			QueryWrapper<User> wrapper = new QueryWrapper<>();
			if (!StringUtils.isEmpty(id))
			{
				wrapper.eq("enabled", 0);
			}

			User userOfDatabase = userMapper.selectById(id);

			if (userOfDatabase != null)
			{
				userMapper.update(userOfDatabase, wrapper);
			}
		}
	}

	/**
	 * 分页获取用户列表
	 *
	 * @param map
	 * @return
	 */
	public Page<UserResponse> findByPageDataScope(Map<String, Object> map)
	{
		try
		{
			//todo
			//分页参数
			String name = (String) map.get("name");
			Integer pageNum = (Integer) map.get("pageNum");
			Integer pageSize = (Integer) map.get("pageSize");

			Page<User> page = new Page<>(pageNum, pageSize);
			//模糊查询参数
			QueryWrapper<User> queryWrapper = new QueryWrapper<>();
			//            if (!org.springframework.util.StringUtils.isEmpty(name)){
			//                queryWrapper.like("name",name);
			//            }
			queryWrapper.eq("is_delete", 0);
			//模糊查询方法
			Page<User> mapIPage = userMapper.selectPage(page, queryWrapper);

			Page<UserResponse> userResponsePage = new Page<>();
			userResponsePage.setCurrent(mapIPage.getCurrent());
			userResponsePage.setSize(mapIPage.getSize());
			userResponsePage.setTotal(mapIPage.getTotal());
			userResponsePage.setPages(mapIPage.getPages());

			List<User> list = mapIPage.getRecords();
			List<UserResponse> listResponse = new ArrayList<>();
			for (User user : list) {
				listResponse.add(toUserResponse(user));
			}
			userResponsePage.setRecords(listResponse);

			return userResponsePage;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	public Integer logicDeleteById(String id)
	{
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(id))
		{
			wrapper.eq("id", id);
		}

		User userOfDatabase = userMapper.selectById(id);
		userOfDatabase.setIsDelete(1);

		if (userOfDatabase != null)
		{
			return userMapper.update(userOfDatabase, wrapper);
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 删除用户
	 *
	 * @param ids
	 */
	public boolean logicBatchDelete(List<String> ids)
	{
		for (String id : ids)
		{
			QueryWrapper<User> wrapper = new QueryWrapper<>();
			if (!StringUtils.isEmpty(id))
			{
				wrapper.eq("id", id);
			}
			User userOfDatabase = userMapper.selectById(id);
			userOfDatabase.setIsDelete(1);
			userMapper.update(userOfDatabase, wrapper);
		}
		return true;
	}

	/**
	 * 设置头像
	 *
	 * @param userImage
	 */
	@Transactional(rollbackFor = Exception.class) public void setUserImage(String id, String userImage)
	{
		Member memberOfDatabase = memberMapper.selectById(id);

		QueryWrapper<Member> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(id))
		{
			wrapper.eq("id", id);
		}

		memberOfDatabase.setAvatar(userImage);
		memberMapper.update(memberOfDatabase, wrapper);
	}

	/**
	 * 设置头像
	 *
	 * @param user
	 */
	@Transactional(rollbackFor = Exception.class) public boolean udpate(User user)
	{

		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(user.getId()))
		{
			wrapper.eq("id", user.getId());
		}
		int rowUser = userMapper.update(user, wrapper);

		QueryWrapper<Member> wrapperMember = new QueryWrapper<>();
		if (!StringUtils.isEmpty(user.getId()))
		{
			wrapper.eq("id", user.getMid());
		}
		int rowMember = memberMapper.update(user.getMember(), wrapperMember);

		if (rowMember == 0 || rowUser == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void configureRoles(String id, List<String> ids)
	{
		for (String temp : ids)
		{
			MemberRole memberRole = new MemberRole();
			memberRole.setCreateDate(new Date());
			memberRole.setMid(id);
			if(StringUtils.isNotEmpty(temp))
			{
				memberRole.setRoleId(temp);
				memberRoleMapper.insert(memberRole);
			}
		}
	}

	public void configureClients(String id, List<String> ids)
	{
		for (String temp : ids)
		{
			MemberClient memberClient = new MemberClient();
			memberClient.setCreateDate(new Date());
			memberClient.setMid(id);
			if(StringUtils.isNotEmpty(temp))
			{
				memberClient.setClientId(temp);
				memberClientMapper.insert(memberClient);
			}
		}
	}
}
