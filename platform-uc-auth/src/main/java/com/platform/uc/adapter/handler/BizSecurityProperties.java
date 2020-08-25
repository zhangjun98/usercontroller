package com.platform.uc.adapter.handler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * 安全配置  在 配置文件里面
 * @author hao.yan
 */
@Data
@ConfigurationProperties(prefix = "zt.security")
public class BizSecurityProperties {

    /**
     * 是否开启安全配置
     */
    private Boolean enable;

    /**
     * 配置需要认证的uri，默认为所有/**
     */
    private String authUri = "/**";

    /**
     * 免认证资源路径，支持通配符
     * 多个值时使用逗号分隔
     */
    private Set<String> anonUris;

    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;

}