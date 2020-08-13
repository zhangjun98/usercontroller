package com.platform.uc.api.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author CDN
 * @desc 系统菜单表
 * Date: 2020-08-10
 */
@Data
@ApiModel(description = "系统菜单表")
public class MenuRequest implements Serializable{


    private static final long serialVersionUID = 24L;

      private String id;
        /**
         * 父菜单
         */
        @ApiModelProperty(value = "父菜单id")
      private String parentId;
        /**
         * 系统模块
         */
        @ApiModelProperty(value = "系统模块id")
      private Long moduleId;
        /**
         * 菜单名称
         */
        @ApiModelProperty(value = "父机构")
      private String name;
        /**
         * 菜单编码
         */
        @ApiModelProperty(value = "菜单编码")
      private String code;
        /**
         * 图标
         */
        @ApiModelProperty(value = "图标")
      private String icon;
        /**
         * 路由
         */
        @ApiModelProperty(value = "路由")
      private String url;
        /**
         * 排序
         */
        @ApiModelProperty(value = "排序")
      private Long seq;
        /**
         * 状态(0--正常 9--删除)
         */
        @ApiModelProperty(value = "状态(0--正常 9--删除)")
      private Long state;

    /**
     * 菜单添加时间
     */
    @ApiModelProperty(value = "菜单添加时间")
    private Date createDate;

    /**
     * 创建人的id
     */
    @ApiModelProperty(value = "创建人的id")
    private Long creatorId;

    /**
     * 更新菜单的时间
     */
    @ApiModelProperty(value = "更新菜单的时间")
    private Date updateDate;

    /**
     * 更新人的id
     */
    @ApiModelProperty(value = "更新人的id")
    private Long updaterId;

    /**
     * 保存子级菜单
     */
    @ApiModelProperty(value = "保存子级菜单")
    private List<MenuRequest> list;

    /**
     * 保存子菜单
     */
    @ApiModelProperty(value = "保存子菜单")
    private List<PermissionRequest> submenuList;
}
