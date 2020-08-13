package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("uc_menu")
public class Menu implements Serializable{


    private static final long serialVersionUID = 24L;

     @TableId(type = IdType.ID_WORKER)
      private String id;
        /**
         * 父菜单
         */
      private String parentId;
        /**
         * 系统模块
         */
      private Long moduleId;
        /**
         * 菜单名称
         */
      private String name;
        /**
         * 菜单编码
         */
      private String code;
        /**
         * 图标
         */
      private String icon;
        /**
         * 路由
         */
      private String url;
        /**
         * 排序
         */
      private Long seq;
        /**
         * 状态(0--正常 9--删除)
         */
      private Integer state;

    /**
     * 菜单添加时间
     */
    private Date createDate;

    /**
     * 创建人的id
     */
    private Long creatorId;

    /**
     * 更新菜单的时间
     */
    private Date updateDate;

    /**
     * 更新人的id
     */
    private Long updaterId;

    /**
     * 保存子级菜单
     */
    @TableField(exist = false)
    private List<Menu> list;

    /**
     * 保存子菜单
     */
    @TableField(exist = false)
    private List<Permission> submenuList;
}
