package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.MemberRoleResponse;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.mapper.RoleMapper;
import com.platform.uc.service.mapper.UcRolePermissionMapper;
import com.platform.uc.service.vo.Role;
import com.platform.uc.service.vo.UcRolePermission;
import com.ztkj.framework.response.core.BizPageResponse;
import javafx.scene.control.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service public class RoleService
{
	@Autowired RoleMapper roleMapper;

	@Autowired MemberRoleMapper memberRoleMapper;

	@Autowired UcRolePermissionMapper rolePermissionMapper;

	public void insert(RoleRequest request)
	{
		Role role = new Role();
		role.setStatus(0);
		role.setCreateDate(new Date());
		role.setCreatorId(request.getOperator());
		role.setCode(request.getCode());
		role.setDescription(request.getDescription());
		role.setName(role.getName());
		role.setOrgId(request.getOrgId());
		roleMapper.insert(role);
	}

	public void update(String id ,RoleRequest request)
	{
		Role role = new Role();
		role.setId(id);
		role.setUpdaterId(request.getOperator());
		role.setStatus(request.getStatus());
		role.setCode(request.getCode());
		role.setDescription(request.getDescription());
		role.setName(role.getName());
		role.setOrgId(request.getOrgId());
		roleMapper.updateById(role);
	}

	public RoleResponse detail(String id)
	{
		RoleResponse roleResponse = new RoleResponse();
		Role role = roleMapper.selectById(id);
		BeanUtils.copyProperties(roleResponse,role);
		return roleResponse;
	}

	public BizPageResponse<RoleResponse> selectByConditions(QueryRoleRequest request)
	{
		Page<Role> dataElementPage = new Page<>(request.getPageNo() == null ? 1 : request.getPageNo(), request.getPageSize() == null ? 10 : request.getPageSize());
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("state", request.getStatus());
		if (StringUtils.isNotEmpty(request.getName()))
		{
			queryWrapper.like("name", request.getName());
		}

		IPage<Role> roleIPage = roleMapper.selectPage(dataElementPage, queryWrapper);
		List<Role> roles = roleIPage.getRecords();
		List<RoleResponse> roleResponses = new ArrayList<>();
		for (Role temp : roles)
		{
			RoleResponse roleResponse = new RoleResponse();
			BeanUtils.copyProperties(roleResponse,temp);
			roleResponses.add(roleResponse);
		}
		BizPageResponse bizPageResponse = new BizPageResponse(roleResponses, roleResponses.size(),request.getPageSize() , request.getPageNo(), roleIPage.getTotal());
		return bizPageResponse;
	}


	public void insertRolePermission(UcRolePermission RolePermission)
	{
		rolePermissionMapper.insert(RolePermission);
	}

	public void changeStatus(BatchRequest request, int i)
	{
		Set<String> ids =  request.getIds();

		for (String temp :ids)
		{
			Role role = new Role();
			role.setStatus(i);
			role.setId(temp);
			roleMapper.updateById(role);
		}
	}


}
