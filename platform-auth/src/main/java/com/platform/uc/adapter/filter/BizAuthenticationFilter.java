package com.platform.uc.adapter.filter;

import com.platform.uc.adapter.handler.BizUserCache;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BizAuthenticationFilter extends OncePerRequestFilter {

    private final BizUserCache userCache;

    public BizAuthenticationFilter(BizUserCache userCache){
        this.userCache = userCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取token
        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            List<Cookie> cookieList  = Arrays.stream(cookies).filter(item->item.getName().equals("token")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cookieList)){
                Cookie cookie = cookieList.get(0);
                UserDetails userDetails = userCache.getUserFromCache(cookie.getValue());
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()));
            }
        }
        chain.doFilter(request, response);
    }

}