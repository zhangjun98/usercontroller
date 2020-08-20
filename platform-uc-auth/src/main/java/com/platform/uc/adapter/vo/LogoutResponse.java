package com.platform.uc.adapter.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutResponse implements Serializable {

    private static final long serialVersionUID = -221291454778953722L;

    /**
     * 跳转地址
     */
    private String redirectUri;

}

