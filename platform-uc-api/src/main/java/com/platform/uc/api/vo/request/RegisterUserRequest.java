package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册用户信息
 * @author hao.yan
 */
@Data
public class RegisterUserRequest implements Serializable {

    private static final long serialVersionUID = -2923240492742053272L;

    private String username;

    private String email;

    private String mobile;

    private String password;

    private RegisterMemberRequest member;

}
