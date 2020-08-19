package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户
 * @author hao.yan
 */
@Data
public class ClientMemberResponse implements Serializable {

    private static final long serialVersionUID = -2435463587454988013L;

    /**
     * 用户标号
     */
    private String id;

    private String uid;

    private String username;

    private String email;

    private String mobile;

    private String name;

    private String nickname;

    private String avatar;

    private String sex;

    private String orgId;

    private Integer status;

    private Date createDate;

    private Date updateDate;

}
