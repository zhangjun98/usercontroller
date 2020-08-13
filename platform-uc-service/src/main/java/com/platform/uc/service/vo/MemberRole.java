package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关系
 */
@Data
@TableName("uc_member_role")
public class MemberRole implements Serializable {

    /**
     * 关系ID
     */
    @TableId
    private String id;

    /**
     * 用户ID
     */
    @TableField("mid")
    private String mid;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

    private Member member;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private Date createTime;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private String mobile;

    @TableField(exist = false)
    private String code;


    @TableField(exist = false)
    private String description;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String orgCode;

    @TableField(exist = false)
    private String orgName;
}
