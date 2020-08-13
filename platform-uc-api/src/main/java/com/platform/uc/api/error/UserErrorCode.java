package com.platform.uc.api.error;

import com.ztkj.framework.response.core.ErrorCode;
import lombok.Getter;

public enum UserErrorCode implements ErrorCode {

    USER_NOTFOUND("200030", "用户不存在"),

    ROLENAME_NOTFOUND("300030", "角色名为空"),

    ID_NOTFOUND("100031", "主键不能为空"),

    PASSWORD_INCONSISTENCY("201100", "密码输入不一致"),

    MEMBER_UPDATE_FAIL("201101", "用户信息更新失败"),

    ROLE_INSERT_FAIL("201301", "角色新增失败"),

    ROLE_UPDATE_FAIL("201302", "角色新增失败"),

    ROLE_NOTFOUND("201303", "未找到角色"),

    ROLE_DELETE_FAIL("201304", "角色删除失败"),

    ROLEID_NOTFOUND("300031", "角色不能为空"),
    PERMISSION_NOTFOUND("300032", "所配置的权限不能为空"),
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
