package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 重置密码
 * @author hao.yan
 */
@Data
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = -5340010309407067582L;

    private String id;

    private String newPassword;

    private String repeatPassword;

}
