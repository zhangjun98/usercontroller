package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class MemberClient implements Serializable {

    /**
     * 关系ID
     */
    @TableId
    private String id;

    /**
     * 用户ID
     */
    @TableField("mid")
    private String mid;

    /**
     * 平台
     */
    @TableField("client_id")
    private String clientId;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

    private Member member;

    private Client client;

}
