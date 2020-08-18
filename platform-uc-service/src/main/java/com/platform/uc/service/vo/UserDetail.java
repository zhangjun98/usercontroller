package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDetail extends Member {

    private static final long serialVersionUID = 6128465769847746298L;

    /**
     * 账户编号
     */
    private String uid;

    private String username;

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
     * 账户的失效时间
     */
    private Date accountExpired;

    /**
     * 账户是否启动
     */
    private boolean enabled;

}
