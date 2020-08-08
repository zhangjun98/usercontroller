package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author hao.yan
 */
@Data
@TableName("uc_members")
public class Member implements Serializable {

    private static final long serialVersionUID = 4020216509360781698L;

    @TableId
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
    @TableField("id_card")
    private String idCard;

    /**
     * 生日
     */
    private Date birthday;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
