package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册用户信息
 * @author hao.yan
 */
@Data
public class RegisterMemberRequest implements Serializable {

    private static final long serialVersionUID = 1882382467422455210L;

    /**
     * 真实名称
     */
    private String name;

    /**
     * 别名
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
     * 身份证号
     */
    private String idCard;

    /**
     * 生日
     */
    private Date birthday;

}
