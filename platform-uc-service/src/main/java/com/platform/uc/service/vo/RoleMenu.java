package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("uc_role_menu")
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = 3653641335200457215L;

	private Long id;

	@TableField("role_id")
	private String roleId;

	@TableField("menu_id")
	private String menuId;

}
