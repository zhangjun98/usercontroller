package com.platform.uc.api.vo.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */
@Getter
@Setter
@ToString
public class Organization implements Serializable{


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
  private List<Organization> list;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Organization that = (Organization) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(parentId, that.parentId) &&
				Objects.equals(orgCode, that.orgCode) &&
				Objects.equals(orgName, that.orgName) &&
				Objects.equals(shortName, that.shortName) &&
				Objects.equals(orgType, that.orgType) &&
				Objects.equals(description, that.description) &&
				Objects.equals(linkMan, that.linkMan) &&
				Objects.equals(phone, that.phone) &&
				Objects.equals(email, that.email) &&
				Objects.equals(address, that.address) &&
				Objects.equals(state, that.state) &&
				Objects.equals(adminImage, that.adminImage) &&
				Objects.equals(createDate, that.createDate) &&
				Objects.equals(creatorId, that.creatorId) &&
				Objects.equals(updateDate, that.updateDate) &&
				Objects.equals(updaterId, that.updaterId);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, parentId, orgCode, orgName, shortName, orgType, description, linkMan, phone, email, address, state, adminImage, createDate, creatorId, updateDate, updaterId);
	}
}
