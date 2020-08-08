package com.platform.uc.adapter.handler;

import com.platform.uc.adapter.contants.AuthorizationContacts;
import com.ztkj.framework.response.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求缓存
 * @author hao.yan
 */
@Slf4j
@Component
public class BizRequestCache implements RequestCache {

    private final RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;

    private final PortResolver portResolver = new PortResolverImpl();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存请求信息
     */
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (requestMatcher.matches(request)) {
            DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);
            String ticket = String.valueOf(System.currentTimeMillis());

            // 把卷放入cookie中
            CookieUtils.set(response, AuthorizationContacts.TICKET, ticket, AuthorizationContacts.TICKET_EXPIRE.intValue());

            // 保存到redis中
            BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(ticket);
            operations.set(savedRequest);
        }else {
            log.debug("Request not saved as configured RequestMatcher did not match");
        }
    }

    /**
     * 获取保存的请求信息
     */
    @Override
    public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtils.get(request, AuthorizationContacts.TICKET);
        if (StringUtils.isEmpty(ticket)){
            return null;
        }
        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(ticket);
        return (SavedRequest) operations.get();
    }

    /**
     * 匹配请求信息
     */
    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = getRequest(request, response);

        if (!matchesSavedRequest(request, saved)) {
            log.debug("saved request doesn't match");
            return null;
        }

        removeRequest(request, response);

        return new SavedRequestAwareWrapper(saved, request);
    }

    private boolean matchesSavedRequest(HttpServletRequest request, SavedRequest savedRequest) {
        if (savedRequest == null) {
            return false;
        }

        if (savedRequest instanceof DefaultSavedRequest) {
            DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) savedRequest;
            return defaultSavedRequest.doesRequestMatch(request, this.portResolver);
        }

        String currentUrl = UrlUtils.buildFullRequestUrl(request);
        return savedRequest.getRedirectUrl().equals(currentUrl);
    }

    /**
     * 移除请求信息
     */
    @Override
    public void removeRequest(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtils.get(request, AuthorizationContacts.TICKET);
        if (StringUtils.isEmpty(ticket)){
            return;
        }
        CookieUtils.set(response, AuthorizationContacts.TICKET, null, 0);
        redisTemplate.delete(ticket);
    }


}
