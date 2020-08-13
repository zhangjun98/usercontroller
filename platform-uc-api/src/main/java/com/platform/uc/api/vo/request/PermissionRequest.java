package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author CDN
 * @desc 菜单的子表
 * Date: 2020-08-11
 */

@Data
public class PermissionRequest implements Serializable{

    private static final long serialVersionUID = 32L;

  private String id;
	/**
	 * 子菜单名称
	 */
  private String name;
	/**
	 * 权限标识
	 */
  private String authIdentification;
	/**
	 * 外键菜单表
	 */
  private String menuId;
	/**
	 * 状态(0--正常 9--删除)
	 */
  private Integer state;
	/**
	 * 创建时间
	 */
  private Date createDate;
	/**
	 * 创建者id
	 */
  private Long creatorId;
	/**
	 * 修改时间
	 */
  private Date updateDate;
	/**
	 * 修改者id
	 */
  private Long updaterId;


}
