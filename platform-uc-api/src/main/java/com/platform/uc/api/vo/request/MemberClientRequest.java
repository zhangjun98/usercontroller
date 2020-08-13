package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberClientRequest implements Serializable {
    /**
     * 关系ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String mid;

    /**
     * 平台
     */
    private String clientId;

    private Date createDate;

    private Date updateDate;

    private String userName;
}
