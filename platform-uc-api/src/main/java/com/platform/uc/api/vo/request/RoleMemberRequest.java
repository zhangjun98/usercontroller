package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMemberRequest implements Serializable {

    private static final long serialVersionUID = -4042760497112636003L;

    /**
     * 用户ID
     */
    private String mid;

    /**
     * 角色ID
     */
    private String roleId;

}
