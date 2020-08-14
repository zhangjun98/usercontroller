package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author hao.yan
 */
@Data
public class TreeMenuResponse implements Serializable {


    private static final long serialVersionUID = -7451840060110765736L;

    private String id;

    private String parentId;

    /**
     * 菜单/按钮名称
     */
    private String name;

    /**
     * 对应路由path
     */
    private String url;

    /**
     * 对应路由组件component
     */
    private String component;

    private String icon;

    private Integer sort;

    private List<TreeMenuResponse> children;

}