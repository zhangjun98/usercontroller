package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 给角色绑定菜单
 * @author hao.yan
 */
@Data
public class BindMenuRequest implements Serializable {

    private static final long serialVersionUID = -4279831238311747153L;

    private String roleId;

    private String menuId;

    private String operator;

}
