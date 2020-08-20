package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户
 */
@Data
@TableName("uc_member_client")
public class MemberClient implements Serializable {

    private static final long serialVersionUID = -8159631955300767911L;

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
     *  0: 未删除
     *  1: 已删除
     */
    @TableField("is_deleted")
    private boolean deleted;

    /**
     * 平台
     */
    @TableField("client_id")
    private String clientId;

    @TableField("org_id")
    private String orgId;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

}
