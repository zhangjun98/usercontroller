package com.platform.uc.adapter.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 客户端
 * @author hao.yan
 */
@Data
public class OAuthClient  implements ClientDetails {

    private static final long serialVersionUID = -2321654521449706199L;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密匙
     */
    private String clientSecret;

    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    private Set<String> resourceIds;

    /**
     * 客户端申请的权限范围
     */
    private Set<String> scope;

    /**
     * 授权类型
     */
    private Set<String> authorizedGrantTypes;

    /**
     * 注册回调地址
     */
    private Set<String> registeredRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    private Collection<GrantedAuthority> authorities;

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
    private Map<String, Object> additionalInformation;

    /**
     * 自动开启范围
     */
    private Set<String> autoApproveScopes;

    /**
     * 秘钥是否需要
     * @return true | false
     */
    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    /**
     * 是否有范围
     * @return true | false
     */
    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    /**
     * 用户是否自动Approval
     * @param scope 范围
     * @return true | false
     */
    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if ("true".equals(auto) || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

}
