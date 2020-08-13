package com.platform.uc.api.error;

import com.ztkj.framework.response.core.ErrorCode;
import lombok.Getter;

public enum RoleErrorCode implements ErrorCode {

    ROLENAME_NOTFOUND("300030", "角色名为空"),
    ID_NOTFOUND("100031", "主键不能为空"),
    ROLEID_NOTFOUND("300031", "角色不能为空"),
    PERMISSION_NOTFOUND("300032", "所配置的权限不能为空"),
    ;

    RoleErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Getter
    private final String code;

    @Getter
    private final String message;

}
