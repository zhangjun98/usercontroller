package com.platform.uc.adapter.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookieUtils  {


    public static void set(HttpServletResponse response, String name, String value, int maxAge){
        // 把卷放入cookie中
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String get(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return null;
        }
        List<Cookie> cookieList  = Arrays.stream(cookies).filter(item->item.getName().equals(name)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cookieList)){
            return null;
        }
        Cookie cookie = cookieList.get(0);
        return cookie.getValue();
    }


}
