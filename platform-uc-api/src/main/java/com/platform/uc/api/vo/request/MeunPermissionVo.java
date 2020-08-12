package com.platform.uc.api.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class MeunPermissionVo
{

	//菜单ID
	private Long meunId;

	//权限ID
	private List<Long> permissionId;

	//角色ID
	private Long roleId;



}
