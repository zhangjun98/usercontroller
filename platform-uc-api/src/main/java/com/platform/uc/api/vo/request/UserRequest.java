package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author hao.yan
 */
@Data
public class UserRequest implements Serializable {

    private static final long serialVersionUID = 9125437343534947924L;

    /**
     * 用户编号
     */
    private String id;

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
    private Date accountExpired;

    /**
     * 账户是否锁定
     */
    private boolean accountLocked;

    /**
     * 证件失效时间
     */
    private Date credentialsExpired;

    /**
     * 账户是否启动
     */
    private boolean enabled;

}
