package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CDN
 * @desc 系统菜单表
 * Date: 2020-08-10
 */
@Data
@TableName("uc_menu")
public class Menu implements Serializable{

    private static final long serialVersionUID = 7083881876490326808L;

    @TableId
    private String id;

    /**
     * 父菜单
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String url;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 状态(0--正常 9--删除)
     */
    private Boolean status;

    /**
     * 菜单添加时间
     */
    private Date createDate;

    /**
     * 创建人的id
     */
    private String creatorId;

    /**
     * 更新菜单的时间
     */
    private Date updateDate;

    /**
     * 更新人的id
     */
    private String updaterId;

}
