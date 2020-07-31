package com.platform.uc.service.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 */
@Data
@TableName("uc_clients")
public class Client implements Serializable {

    private static final long serialVersionUID = 3013135479245557894L;

    /**
     * 客户端ID
     */
    @TableId
    private String id;

    /**
     * 客户端密匙
     */
    private String secret;

    /**
     * 开发者编号
     */
    @TableField("developer_id")
    private String developerId;

    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    @TableField("resource_ids")
    private String resourceIds;

    /**
     * 客户端申请的权限范围
     */
    private String scope;

    /**
     * 授权类型
     */
    @TableField("grant_types")
    private String grantTypes;

    /**
     * 注册回调地址
     */
    @TableField("redirect_urls")
    private String redirectUrls;

    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    private String authorities;

    /**
     * 访问令牌有效期
     */
    @TableField("access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新令牌有效期
     */
    @TableField("refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    /**
     * 预留字段
     */
    @TableField("additional_information")
    private String additionalInformation;

    /**
     * 自动开启范围
     */
    @TableField("auto_approve_scopes")
    private String autoApproveScopes;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


}
