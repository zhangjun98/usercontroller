package com.platform.uc.api.vo.response;

import lombok.Data;

@Data
public class RoleMenuResponse {

    private String id;

    private String roleId;

    private String menuId;

    private String menuName;

    private String parentId;

}
