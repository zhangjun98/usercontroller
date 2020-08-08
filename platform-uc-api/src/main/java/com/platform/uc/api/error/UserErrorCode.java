package com.platform.uc.api.error;

import com.ztkj.framework.response.core.ErrorCode;
import lombok.Getter;

public enum UserErrorCode implements ErrorCode {

    USER_NOTFOUND("200030", "用户不存在"),

    ;

    UserErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Getter
    private final String code;

    @Getter
    private final String message;

}
