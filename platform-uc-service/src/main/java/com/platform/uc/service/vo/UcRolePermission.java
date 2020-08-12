package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UcRolePermission {

	private Long id;

	@TableField("role_id")
	private Long roleId;

	@TableField("module_id")
	private Long moduleId;

	@TableField("menu_id")
	private Long menuId;

	@TableField("permission_id")
	private Long permissionId;

}
