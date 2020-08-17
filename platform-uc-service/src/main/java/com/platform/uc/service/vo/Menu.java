package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单实体
 * @author hao.yan
 */
@Data
@TableName("uc_menu")
public class Menu implements Serializable{

    private static final long serialVersionUID = 7083881876490326808L;


    @TableId
    private String id;

    /**
     * 父机节点
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
     * 路由地址
     */
    private String path;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 类型
     * 1: 菜单
     * 2: 按钮
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0--正常 9--删除)
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

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
