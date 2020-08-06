package com.platform.uc.adapter.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author hao.yan
 */
@Data
public class MemberResponse implements Serializable {

    private static final long serialVersionUID = -781808706668592421L;

    /**
     * 用户编号
     */
    private String id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 显示名称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

}
