package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开发者
 * @author hao.yan
 */
@Data
@TableName("uc_developer")
public class Developer implements Serializable {

    private static final long serialVersionUID = -822068541001776978L;

    /**
     * 开发者编号
     */
    @TableId
    private String id;

    /**
     * 开发者名称
     */
    private String name;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


}
