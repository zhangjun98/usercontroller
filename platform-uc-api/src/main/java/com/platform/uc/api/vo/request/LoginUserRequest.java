package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户账号查询
 * @author hao.yan
 */
@Data
public class LoginUserRequest implements Serializable {

    private static final long serialVersionUID = -3293089702614049039L;

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

}
