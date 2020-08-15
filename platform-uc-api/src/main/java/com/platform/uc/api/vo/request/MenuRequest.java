package com.platform.uc.api.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 菜单实体对象
 * @author hao.yan
 */
@Data
@ApiModel(description = "菜单实体对象")
public class MenuRequest implements Serializable{

    private static final long serialVersionUID = -2485397326130062396L;

    /**
     * 父机节点
     */
    @ApiModelProperty(value = "父机节点")
    private String parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /**
     * 对应路由组件component
     */
    @ApiModelProperty(value = "路由地址")
    private String component;

    /**
     * 类型
     * 1: 菜单
     * 2: 按钮
     */
    @ApiModelProperty(value = "类型 1: 菜单 2: 按钮")
    private Integer type;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operator;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
