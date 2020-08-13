package com.platform.uc.api.vo.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author hao.yan
 */
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 4020216509360781698L;

    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 显示名称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 生日
     */
    private Date birthday;

    private Date createTime;

    private Date updateTime;

}
