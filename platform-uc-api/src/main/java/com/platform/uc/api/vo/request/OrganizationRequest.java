package com.platform.uc.api.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */
@Data
@ToString
@ApiModel(description = "组织机构表")
public class OrganizationRequest implements Serializable{

    private static final long serialVersionUID = 5272723848480230740L;

	/**
	* 父机构
	*/
	@ApiModelProperty(value = "父机构")
	private String parentId;

	/**
	* 机构编码
	*/
	@ApiModelProperty(value = "机构编码")
	private String code;

	/**
	* 机构名称
	*/
	@ApiModelProperty(value = "机构名称")
	private String name;

	/**
	* 简称
	*/
	@ApiModelProperty(value = "简称")
	private String shortName;

	/**
	* 机构类型
	*/
	@ApiModelProperty(value = "机构类型")
	private Integer type;

	/**
	* 机构描述信息
	*/
	@ApiModelProperty(value = "机构描述信息")
	private String description;

	/**
	 * 机构管理员ID
	 */
	@ApiModelProperty(value = "机构管理员ID")
	private String mid;

	/**
	* 联系地址
	*/
	@ApiModelProperty(value = "机构名称")
	private String address;

	/**
	* 状态(0--正常 9--删除)
	*/
	@ApiModelProperty(value = "状态(0--正常 9--删除)")
	private Integer status;

	@ApiModelProperty(value = "操作人")
	private String operator;
}
