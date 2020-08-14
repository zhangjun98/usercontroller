package com.platform.uc.api.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationResponse implements Serializable {

    private static final long serialVersionUID = 3869496454121432677L;

    private String id;

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
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

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

}
