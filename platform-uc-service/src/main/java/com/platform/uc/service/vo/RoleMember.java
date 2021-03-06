package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class RoleMember implements Serializable {

    private static final long serialVersionUID = -2435463587454988013L;

    /**
     * 关系ID
     */
    @TableId(type = IdType.AUTO)
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

}
