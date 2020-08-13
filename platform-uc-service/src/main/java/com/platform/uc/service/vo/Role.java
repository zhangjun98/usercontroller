package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 */
@Data
@TableName(value = "uc_role")
public class Role implements Serializable {

	private static final long serialVersionUID = -5710710681616739771L;

	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	@TableField(value = "`name`")
	private String name;

	@TableField(value = "`code`")
	private String code;

	@TableField(value = "description")
	private String description;

	@TableField(value = "org_id")
	private String orgId;

	private int status;

	@TableField(value = "create_date")
	private Date createDate;

	@TableField(value = "creator_id")
	private String creatorId;

	@TableField(value = "update_date")
	private Date updateDate;

	@TableField(value = "updater_id")
	private String updaterId;

}
