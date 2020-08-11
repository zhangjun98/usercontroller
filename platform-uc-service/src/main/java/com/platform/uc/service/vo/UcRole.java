package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "uc_role")
public class UcRole implements Serializable
{

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	@TableField(value = "`name`")
	private String name;

	@TableField(value = "`code`")
	private String code;

	@TableField(value = "description")
	private String description;

	@TableField(value = "permission_type")
	private int permissionType;

	@TableField(value = "state")
	private int state;

	@TableField(value = "create_date")
	private Date createDate;

	@TableField(value = "creator_id")
	private Long creatorId;

	@TableField(value = "update_date")
	private Date updateDate;

	@TableField(value = "updater_id")
	private Long updaterId;

	@TableField(value = "organ_id")
	private Long organId;
}
