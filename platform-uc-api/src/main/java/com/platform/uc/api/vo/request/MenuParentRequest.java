package com.platform.uc.api.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 根据父节点查询子菜单
 * @author hao.yan
 */
@Data
@ApiModel(description = "根据父节点查询子菜单")
public class MenuParentRequest implements Serializable {

    private static final long serialVersionUID = 6763263157371681860L;

    /**
     * 父节点集合
     */
    @ApiModelProperty(value = "父机节点", required = true)
    private Set<String> pids;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = false)
    private Integer status = 1;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = false)
    private String searchName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = false)
    private Integer type;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", required = false)
    private String mid;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号", required = false)
    private String roleId;

}
