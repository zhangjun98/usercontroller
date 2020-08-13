package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.mapper.RoleMapper;
import com.platform.uc.service.mapper.UcRolePermissionMapper;
import com.platform.uc.service.vo.RoleMember;
import com.platform.uc.service.vo.Role;
import com.platform.uc.service.vo.UcRolePermission;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private MemberRoleMapper memberRoleMapper;

	@Resource
	private UcRolePermissionMapper ucRolePermissionMapper;

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
		int count = roleMapper.updateById(role);
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


	@Transactional
	public List<RoleMember> configurePermissions(List<MeunPermissionVo> meunPermissionVos) {
		//入库列表
		List<UcRolePermission> ucRolePermissions = new ArrayList<>();
		for (MeunPermissionVo meunPermissionVo : meunPermissionVos)
		{
			if (meunPermissionVo.getPermissionId() == null || meunPermissionVo.getPermissionId().size() <= 0)
			{
				continue;
			}
			for (Long temp : meunPermissionVo.getPermissionId())
			{
				UcRolePermission ucRolePermission = new UcRolePermission();
				ucRolePermission.setMenuId(meunPermissionVo.getMeunId());
				ucRolePermission.setRoleId(meunPermissionVo.getRoleId());
				ucRolePermission.setPermissionId(temp);
			}

		}
		return null;
	}

	public void insertRolePermission(UcRolePermission ucRolePermission)
	{

		ucRolePermissionMapper.insert(ucRolePermission);
	}
}
