package com.platform.uc.api.vo.request;

import com.ztkj.framework.response.core.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询机构参数")
public class QueryOrganizationRequest extends Page{

	private static final long serialVersionUID = -3886745279151715272L;

	private String id;

	private String searchName;

	/**
	* 父机构
	*/
	@ApiModelProperty(value = "父机构")
	private String parentId;

	/**
	* 机构类型
	*/
	@ApiModelProperty(value = "机构类型")
	private Integer type;

	/**
	* 状态(0--正常 9--删除)
	*/
	@ApiModelProperty(value = "状态(0--正常 9--删除)")
	private Integer status;


}
