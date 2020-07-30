package com.platform.uc.adapter.configure;

import com.platform.uc.adapter.handler.CustomOAuth2AuthenticationEntryPoint;
import com.platform.uc.adapter.handler.CustomWebResponseExceptionTranslator;
import com.platform.uc.adapter.service.AuthorizationCodeService;
import com.platform.uc.adapter.service.BizClientDetailsService;
import com.platform.uc.adapter.store.BizTokenStore;
import com.ztkj.framework.common.authorization.handler.FrameworkAccessTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import javax.annotation.Resource;

/**
 * @author hao.yan
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfigure extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private BizClientDetailsService bizClientDetailsService;

    @Resource
    private CustomOAuth2AuthenticationEntryPoint customOAuth2AuthenticationEntryPoint;

    @Resource
    private BizTokenStore tokenStore;

    /**
     * 授权码
     */
    @Resource
    private AuthorizationCodeService authorizationCodeService;

    /**
     * 客户端配置  clientid clientSercrt
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(bizClientDetailsService);
    }


    /**
     * 认证方式及token配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenServices(createDefaultTokenServices())
                .authorizationCodeServices(authorizationCodeService);
        // 自定义异常转换类
        endpoints.exceptionTranslator(new CustomWebResponseExceptionTranslator());
    }

    @Primary
    @Bean
    public DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        // 是否支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 是否重新使用刷新令牌
        tokenServices.setReuseRefreshToken(true);
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
        // 默认30天，这里修改
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        tokenServices.setClientDetailsService(bizClientDetailsService);
        return tokenServices;
    }

    /**
     * token的访问权限表达式配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("permitAll()");
        // 支持client_id以及client_secret作登录认证
        security.allowFormAuthenticationForClients();
        security.authenticationEntryPoint(customOAuth2AuthenticationEntryPoint);
    }

    @Bean
    public FrameworkAccessTokenConverter accessTokenConverter() {
        return new FrameworkAccessTokenConverter();
    }

}
