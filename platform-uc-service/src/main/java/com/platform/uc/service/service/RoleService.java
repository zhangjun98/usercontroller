package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.UcRoleMapper;
import com.platform.uc.service.vo.UcMemberRole;
import com.platform.uc.service.vo.UcRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class RoleService
{
	@Autowired UcRoleMapper ucRoleMapper;

	public void insert(UcRole ucRole)
	{
		ucRoleMapper.insert(ucRole);
	}

	public void update(UcRole ucRole)
	{
		ucRoleMapper.updateById(ucRole);
	}

	public UcRole selectBean(Long id)
	{
		return ucRoleMapper.selectById(id);
	}

	public List<UcRole> selectList(String name)
	{
		QueryWrapper<UcRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("state",0);
		if (StringUtils.isNotEmpty(name))
		{
			queryWrapper.like("name", name);
		}
		return ucRoleMapper.selectList(queryWrapper);
	}

	public List<UcMemberRole> selectRoleUsers(Long roleId)
	{
		return ucRoleMapper.selectList(roleId);
	}
}
