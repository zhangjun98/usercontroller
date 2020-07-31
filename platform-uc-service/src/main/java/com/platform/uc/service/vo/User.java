package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 */
@Data
@TableName("uc_users")
public class User implements Serializable {

    private static final long serialVersionUID = 8802724222925902827L;

    /**
     * 账户编号
     */
    @TableId
    private String id;

    /**
     * 用户编号
     */
    private String mid;

    /**
     * 账户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户过期时间
     */
    @TableField("account_expired")
    private Date accountExpired;

    /**
     * 账户是否锁定
     */
    @TableField("account_locked")
    private boolean accountLocked;

    /**
     * 证件失效时间
     */
    @TableField("credentials_expired")
    private Date credentialsExpired;

    /**
     * 账户是否启动
     */
    private boolean enabled;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


}
