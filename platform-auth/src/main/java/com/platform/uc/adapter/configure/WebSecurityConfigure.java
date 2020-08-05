package com.platform.uc.adapter.configure;

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

    @Resource
    private BizLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private BizAuthenticationHandler authenticationHandler;

    @Resource
    private BizSecurityProperties ignoreProperties;

    @Resource
    private SecurityAuthenticationProvider authenticationProvider;

//    @Resource
//    private BizRequestCache requestCache;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!CollectionUtils.isEmpty(ignoreProperties.getAnonUris())) {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                    .authorizeRequests();
            ignoreProperties.getAnonUris()
                    .forEach(url -> registry.antMatchers(url).permitAll());
        }
//        http.setSharedObject(RequestCache.class, requestCache);
        http
                .httpBasic()
                    .authenticationEntryPoint(new BizAuthExceptionEntryPoint())
                .and()
                    // 设置成为无状态
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .cors().disable()
                .csrf().disable()
//                    .requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**")
//                .and()

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
                    .successHandler(authenticationHandler)
                    .failureHandler(authenticationHandler)
                .and()
                    .logout().permitAll()
                    // /logout退出清除cookie
                    .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                    .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                    // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                    .exceptionHandling().accessDeniedHandler(new BizAccessDeniedHandler())
                ;

        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();
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
