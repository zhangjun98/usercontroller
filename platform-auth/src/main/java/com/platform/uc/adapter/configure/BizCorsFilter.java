//package com.platform.uc.adapter.configure;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Order(1)
//@Component
//public class BizCorsFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        res.addHeader("Access-Control-Allow-Credentials", "true");
//        res.addHeader("Access-Control-Allow-Origin", "*");
//        res.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
//        res.addHeader("Access-Control-Allow-Headers", "*");
//
//        //res.addHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Access-Control-Request-Headers, Access-Control-Request-Method, Authorization, X-Requested-With, User-Agent, Referer, Origin");
//        res.setHeader("Access-Control-Max-Age", "1728000");
//        if (req.getMethod().equals("OPTIONS"))
////            response.setStatus(HttpServletResponse.SC_OK);
//            res.setStatus(HttpServletResponse.SC_OK);
//        else
//        chain.doFilter(request, response);
//    }
//    @Override
//    public void destroy() {
//    }
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//}