package com.platform.uc.api.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 修改密码
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ForgotPasswordRequest extends ResetPasswordRequest {

    private static final long serialVersionUID = -9064629593929617815L;

    private String oldPassword;

}