package com.platform.uc.api.vo.request;

import lombok.Data;

@Data
public class UcRolePermission
{

	private Long id;

	private Long roleId;

	private Long moduleId;

	private Long menuId;

	private Long permissionId;

}
