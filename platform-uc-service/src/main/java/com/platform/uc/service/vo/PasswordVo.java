package com.platform.uc.service.vo;

import lombok.Data;

@Data
public class PasswordVo {

    private String id;

    private String oldPassword;

    private String newPassword;

    private String repeatPassword;
}