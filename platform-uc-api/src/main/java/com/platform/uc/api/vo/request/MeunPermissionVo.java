package com.platform.uc.api.vo.request;

import lombok.Data;

import java.util.List;

@Data
public class MeunPermissionVo {

	//菜单ID
	private String meunId;

	//权限ID
	private List<String> permissionId;

	//角色ID
	private String roleId;

}
