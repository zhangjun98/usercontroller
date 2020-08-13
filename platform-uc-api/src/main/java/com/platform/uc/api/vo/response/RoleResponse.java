package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色返回对象
 * @author hao.yan
 */
@Data
public class RoleResponse implements Serializable {

    private static final long serialVersionUID = -4522559037601865148L;

    private String id;

    private String name;

    private String code;

    private String description;

    private int status;

    private String orgId;

    private Date createDate;

    private Date updateDate;

}
