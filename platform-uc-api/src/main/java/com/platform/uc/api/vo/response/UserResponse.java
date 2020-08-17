package com.platform.uc.api.vo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 用户信息返回
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends MemberResponse {

    private static final long serialVersionUID = -3814579972074311736L;

    /**
     * 账户编号
     */
    private String uid;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色编号
     */
    private Set<String> roleIds;

    /**
     * 账户是否失效
     */
    private boolean accountNonExpired = true;

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

}
