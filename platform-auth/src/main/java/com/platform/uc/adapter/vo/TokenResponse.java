package com.platform.uc.adapter.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 9003878764738760129L;

    private String accessToken;

    private Integer accessExpire;

    private String refreshToken;

    private Integer refreshExpire;

    private String type;

}