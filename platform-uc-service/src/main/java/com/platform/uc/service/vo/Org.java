package com.jx.eat.domain;
import java.io.Serializable;


/**
 * @author CDN
 * @desc 组织机构表
 * Date: 2020-08-11
 */

public class Org implements Serializable{

    private static final long serialVersionUID = 22L;

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
  private java.util.Date createDate;
  private Long creatorId;
  private java.util.Date updateDate;
  private Long updaterId;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }


  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }


  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }


  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }


  public Long getOrgType() {
    return orgType;
  }

  public void setOrgType(Long orgType) {
    this.orgType = orgType;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getLinkMan() {
    return linkMan;
  }

  public void setLinkMan(String linkMan) {
    this.linkMan = linkMan;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public Long getState() {
    return state;
  }

  public void setState(Long state) {
    this.state = state;
  }


  public String getAdminImage() {
    return adminImage;
  }

  public void setAdminImage(String adminImage) {
    this.adminImage = adminImage;
  }


  public java.util.Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.util.Date createDate) {
    this.createDate = createDate;
  }


  public Long getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }


  public java.util.Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(java.util.Date updateDate) {
    this.updateDate = updateDate;
  }


  public Long getUpdaterId() {
    return updaterId;
  }

  public void setUpdaterId(Long updaterId) {
    this.updaterId = updaterId;
  }

}
