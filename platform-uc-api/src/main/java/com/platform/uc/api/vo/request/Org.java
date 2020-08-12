package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */
@Data
public class Org implements Serializable{


    private static final long serialVersionUID = 5272723848480230740L;

    private Long id;
	/**
	 * 父机构
	 */
  private Long parentId;
	/**
	 * 机构编码
	 */
  private String orgCode;
	/**
	 * 机构名称
	 */
  private String orgName;
	/**
	 * 简称
	 */
  private String shortName;
	/**
	 * 机构类型
	 */
  private Long orgType;
	/**
	 * 机构描述信息
	 */
  private String description;
	/**
	 * 联系人
	 */
  private String linkMan;
	/**
	 * 联系电话
	 */
  private String phone;
	/**
	 * 邮箱
	 */
  private String email;
	/**
	 * 联系地址
	 */
  private String address;
	/**
	 * 状态(0--正常 9--删除)
	 */
  private Long state;
	/**
	 * 管理员图片
	 */
  private String adminImage;

  private Date createDate;
  private Long creatorId;
  private Date updateDate;
  private Long updaterId;

    /**
     * 装子级的机构
     */
  private List<Org> list;

}
