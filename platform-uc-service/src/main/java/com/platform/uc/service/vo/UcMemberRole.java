package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "uc_scopes")
public class UcMemberRole
{

	@TableId(type = IdType.ASSIGN_ID)
	private String id;


	@TableField(value = "mid")
	private String mid;


	@TableField(value = "`role_id`")
	private String roleId;

	@TableField(value = "create_time")
	private Date createTime;

	@TableField(value = "update_time")
	private Date updateTime;

	@TableField(exist = false)
	private String userName;

}
