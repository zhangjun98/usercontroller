package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */
@Data
@TableName("uc_org")
public class Organization implements Serializable{

	private static final long serialVersionUID = 5272723848480230740L;

	private String id;

	/**
	* 父机构
	*/
	private String parentId;

	/**
	* 机构编码
	*/
	private String code;

	/**
	* 机构名称
	*/
	private String name;

	/**
	* 简称
	*/
	private String shortName;

	/**
	* 机构类型
	*/
	private Long type;

	/**
	* 机构描述信息
	*/
	private String description;

	/**
	 * 机构管理员ID
	 */
	private String mid;

	/**
	* 联系地址
	*/
	private String address;

	/**
	* 状态(0--正常 9--删除)
	*/
	private Integer status;

	private Date createDate;

	private String creatorId;

	private Date updateDate;

	private String updaterId;

}
