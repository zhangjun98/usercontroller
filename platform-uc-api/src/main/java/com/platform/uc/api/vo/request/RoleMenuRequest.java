package com.platform.uc.api.vo.request;

import lombok.Data;

@Data
public class RoleMenuRequest {

    private String id;

    private String roleId;

    private String menuId;

    private String menuName;

    private String parentId;
}
