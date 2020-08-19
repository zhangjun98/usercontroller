package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.RoleMenuResponse;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.service.mapper.RoleMapper;
import com.platform.uc.service.mapper.RoleMenuMapper;
import com.platform.uc.service.vo.Role;
import com.platform.uc.service.vo.RoleMenu;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private RoleMenuMapper roleMenuMapper;

	public void insert(RoleRequest request){
		Role role = BeanUtils.toT(request, Role.class);
		role.setCreateDate(new Date());
		role.setCreatorId(request.getOperator());
		int count = roleMapper.insert(role);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_INSERT_FAIL);
		}
	}

	public void update(String id, RoleRequest request) {
		Role role = roleMapper.selectById(id);
		if (role == null){
			throw new BizException(UserErrorCode.ROLE_NOTFOUND);
		}
		Role newRole = BeanUtils.toT(request, Role.class);
		newRole.setId(role.getId());
		newRole.setUpdateDate(new Date());
		newRole.setUpdaterId(request.getOperator());
		int count = roleMapper.updateById(newRole);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_UPDATE_FAIL);
		}
	}

	public RoleResponse detail(String id) {
		Role role = roleMapper.selectById(id);
		return BeanUtils.toT(role, RoleResponse.class);
	}

	/**
	 * 查询角色列表
	 */
	public BizPageResponse<RoleResponse> selectByConditions(QueryRoleRequest request) {
		Page<Role> page = new Page<>();
		page.setCurrent(request.getPageNo());
		page.setSize(request.getPageSize());
		QueryWrapper<Role> wrapper = new QueryWrapper<>();
		wrapper.eq("status", request.getStatus());
		if (StringUtils.isNotEmpty(request.getName())) {
			wrapper.like("name", request.getName());
		}
		IPage<Role> roles = roleMapper.selectPage(page, wrapper);
		if (CollectionUtils.isEmpty(roles.getRecords())){
			return BizPageResponseUtils.success(new ArrayList<>());
		}
		List<RoleResponse> responses = BeanUtils.toList(roles.getRecords(), RoleResponse.class);
		return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), responses);
	}

	/**
	 * 批量更新状态
	 * 0: 正常
	 * 1: 删除状态
	 */
	public void changeStatus(BatchRequest request, Integer status){
		UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
		wrapper.in("id", request.getIds());
		Role role = new Role();
		role.setStatus(status);
		role.setUpdaterId(request.getOperator());
		role.setUpdateDate(new Date());
		int count = roleMapper.update(role, wrapper);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_DELETE_FAIL);
		}
	}

	/**
	 * 绑定菜单
	 */
	public void bindMenus(Collection<BindMenuRequest> requests){
		if (CollectionUtils.isEmpty(requests)){
			throw new BizException(UserErrorCode.ROLE_MENU_BIND_FAIL);
		}
		List<RoleMenu> roleMenus = requests.stream()
				.map(item->BeanUtils.toT(item, RoleMenu.class))
				.collect(Collectors.toList());
		roleMenuMapper.insertBatch(roleMenus);
	}

	/**
	 * 根据用户编号查询角色
	 */
	public List<RoleResponse> selectByMid(String mid){
		List<Role> roles = roleMapper.selectByMid(mid);
		return BeanUtils.toList(roles, RoleResponse.class);
	}

	/**
	 * 查询角色菜单列表
	 */
	public List<RoleMenuResponse>findRoleMenusByRoleId(String roleId) {
		return roleMenuMapper.selectMenusByRole(roleId);
	}

}
