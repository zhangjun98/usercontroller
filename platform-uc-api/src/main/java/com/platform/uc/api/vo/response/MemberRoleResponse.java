package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberRoleResponse implements Serializable {

    /**
     * 关系ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String mid;

    /**
     * 角色ID
     */
    private String roleId;

    private Date createDate;

    private Date updateDate;

    private String userName;

}
