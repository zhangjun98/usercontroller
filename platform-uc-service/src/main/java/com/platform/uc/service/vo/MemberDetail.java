package com.platform.uc.service.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author hao.yan
 */
@Data
public class MemberDetail  implements Serializable {

    private static final long serialVersionUID = 1494166605099657856L;

    /**
     * 用户编号
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

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
     * 生日
     */
    private Date birthday;

    /**
     * 身份真
     */
    private String idCard;

    /**
     * 状态
     */
    private Integer status;

    private Date createTime;

}
