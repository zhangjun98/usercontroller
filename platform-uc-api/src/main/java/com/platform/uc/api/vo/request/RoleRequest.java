package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 */
@Data
public class RoleRequest implements Serializable {

	private static final long serialVersionUID = -1652198670656145760L;

	private String name;

	private String code;

	private String description;

	private String orgId;

	private String operator;


}
