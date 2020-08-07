package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求的范围
 * @author hao.yan
 */
@Data
@TableName(value = "uc_scopes")
public class Scope implements Serializable {

    private static final long serialVersionUID = -8435252399950427822L;

    @TableId
    private String id;

    /**
     * 范围名称
     * 全局唯一
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 请求范围描述
     */
    @TableField(value = "`describe`")
    private String describe;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

}
