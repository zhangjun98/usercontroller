package com.platform.uc.adapter.filter;

import com.platform.uc.adapter.handler.BizUserCache;
import com.ztkj.framework.response.utils.CookieUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录后 lt(login token) 验证
 * @author hao.yan
 */
public class BizAuthenticationFilter extends OncePerRequestFilter {

    private final BizUserCache userCache;

    public BizAuthenticationFilter(BizUserCache userCache){
        this.userCache = userCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取token
        String token = CookieUtils.get(request, "lt");
        if (!StringUtils.isEmpty(token)){
            UserDetails userDetails = userCache.selectUserDetails(token);
            if (userDetails != null) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            }
        }
        chain.doFilter(request, response);
    }

}