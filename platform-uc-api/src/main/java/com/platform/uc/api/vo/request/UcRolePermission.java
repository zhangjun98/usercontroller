package com.platform.uc.api.vo.request;

import lombok.Data;

@Data
public class UcRolePermission
{

	private String id;

	private String roleId;

	private Long moduleId;

	private String menuId;

	private String permissionId;

}
