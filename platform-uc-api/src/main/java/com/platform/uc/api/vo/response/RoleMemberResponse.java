package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关系
 */
@Data
public class RoleMemberResponse implements Serializable {

    private static final long serialVersionUID = -2435463587454988013L;

    /**
     * ID
     */
    private String id;

    private String uid;

    private String username;

    private String email;

    private String mobile;

    private String name;

    private String nickname;

    private String avatar;

    private String sex;

    private Date createDate;

    private Date updateDate;

}
