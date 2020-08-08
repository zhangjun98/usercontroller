package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author hao.yan
 */
@Data
public class MemberResponse implements Serializable {


    private static final long serialVersionUID = 4700966728478527499L;

    /**
     * 用户编号
     */
    private String id;

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

    private String idCard;

    private Date createTime;

    private Date updateTime;

}
