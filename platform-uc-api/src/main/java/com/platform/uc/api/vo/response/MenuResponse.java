package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 菜单
 * @author hao.yan
 */
@Data
public class MenuResponse implements Serializable{

    private static final long serialVersionUID = 1443168361935089124L;

    private String id;

    /**
     * 父菜单
     */
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
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0--正常 9--删除)
     */
    private Boolean status;

    /**
     * 创建人
     */
    private Date createDate;

    /**
     * 更新人
     */
    private Date updateDate;

}
