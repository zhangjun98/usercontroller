package com.platform.uc.adapter.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应
 * @author hao.yan
 */
@Data
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -781808706668592421L;

    /**
     * 登录后的token
     */
    private String token;

    /**
     * 失效时间
     */
    private Long expire;

    /**
     * 跳转地址
     */
    private String redirectUri;

}
