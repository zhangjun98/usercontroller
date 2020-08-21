package com.platform.uc.adapter.configure;

import com.platform.uc.adapter.filter.BizAuthenticationFilter;
import com.platform.uc.adapter.handler.*;
import com.ztkj.framework.common.authorization.handler.BizAccessDeniedHandler;
import com.ztkj.framework.common.authorization.handler.BizAuthExceptionEntryPoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * 安全认证
 * @author hao.yan
 */
@Configuration
@EnableConfigurationProperties(value = {BizSecurityProperties.class})
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
    /**
     * 登出成功拦截器
     */
    @Resource
    private BizLogoutSuccessHandler logoutSuccessHandler;
    /**
     * 授权处理
     */
    @Resource
    private BizAuthenticationHandler authenticationHandler;
    /**
     * 自定义配置文件
     */
    @Resource
    private BizSecurityProperties ignoreProperties;

    @Resource
    private SecurityAuthenticationProvider authenticationProvider;
    /**
     * 请求缓存
     */
    @Resource
    private BizRequestCache requestCache;
    /**
     * 用户信息缓存
     */
    @Resource
    private BizUserCache userCache;

    /**
     * 密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!CollectionUtils.isEmpty(ignoreProperties.getAnonUris())) {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                    .authorizeRequests();
            //配置允许访问的路径
            ignoreProperties.getAnonUris()
                    .forEach(url -> registry.antMatchers(url).permitAll());
        }

        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

        http.setSharedObject(RequestCache.class, requestCache);
        http
            // 开启跨域共享
            .cors().disable()
            // 跨域伪造请求限制=无效
            .csrf().disable()

            .httpBasic()
                .authenticationEntryPoint(new BizAuthExceptionEntryPoint())
            .and()
                // 设置成为无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
//                    // 放开option请求
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                // 监控端点内部放行
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/").loginProcessingUrl("/login")
                .failureUrl("/?code=")
                //登录成功与否之后进行权限处理
                .successHandler(authenticationHandler)
                .failureHandler(authenticationHandler)
            .and()
                .logout().permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("lt", "remember-me"))
                .logoutSuccessHandler(logoutSuccessHandler)
            .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling().accessDeniedHandler(new BizAccessDeniedHandler())
            ;

        // 配置token验证过滤器
        http.addFilterBefore(new BizAuthenticationFilter(userCache), UsernamePasswordAuthenticationFilter.class);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

}
