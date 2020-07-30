package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientRequest implements Serializable {

    private static final long serialVersionUID = -4690485710552675686L;

    /**
     * 客户端ID
     */
    private String id;

    /**
     * 客户端密匙
     */
    private String secret;

    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    private String resourceIds;

    /**
     * 客户端申请的权限范围
     */
    private String scope;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 注册回调地址
     */
    private String redirectUrls;

    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    private String authorities;

    /**
     * 访问令牌有效期
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新令牌有效期
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 预留字段
     */
    private String additionalInformation;

    /**
     * 自动开启范围
     */
    private String autoApproveScopes;

}
