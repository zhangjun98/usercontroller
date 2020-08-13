package com.platform.uc.api.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关系
 */
@Data
public class MemberRoleResponse implements Serializable {

    /**
     * 关系ID
     */
    @TableId
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

    private Date createTime;

    private String email;

    private String mobile;

    private String code;


    private String description;

    private String name;

    private String orgCode;

    private String orgName;
}
