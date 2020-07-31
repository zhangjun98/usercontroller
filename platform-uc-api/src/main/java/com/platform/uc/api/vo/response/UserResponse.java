package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 用户信息返回
 * @author hao.yan
 */
@Data
public class UserResponse implements Serializable {

    private static final long serialVersionUID = -3814579972074311736L;

    /**
     * 用户编号
     */
    private String id;

    /**
     * 用户编号
     */
    private MemberResponse member;

    /**
     * 显示名称
     */
    private String nickname;

    /**
     * 账户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色名称
     */
    private Collection<String> roles;

    /**
     * 账户是否失效
     */
    private boolean accountExpired = true;

    /**
     * 账户是否锁定
     */
    private boolean accountLocked;

    /**
     * 证件是否失效
     */
    private boolean credentialsExpired = true;

    /**
     * 账户是否启动
     */
    private boolean enabled;

    private Date createTime;

    private Date updateTime;

}
