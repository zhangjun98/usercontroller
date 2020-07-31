package com.platform.uc.adaper.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author hao.yan
 */
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = -237553781141937805L;

    /**
     * 用户编号
     */
    private String id;

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
