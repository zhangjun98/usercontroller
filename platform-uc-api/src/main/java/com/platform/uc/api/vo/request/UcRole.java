package com.platform.uc.api.vo.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UcRole implements Serializable
{

	private String id;

	private String name;

	private String code;

	private String description;

	private int permissionType;

	private int state;

	private Date createDate;

	private Long creatorId;

	private Date updateDate;

	private Long updaterId;

	private String orgId;
}
