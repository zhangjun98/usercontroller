package com.platform.uc.adapter.handler;

import com.platform.uc.adapter.contants.AuthorizationContacts;
import com.ztkj.framework.response.utils.CookieUtils;
import com.ztkj.framework.response.utils.SnowflakeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
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

    private final SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存请求信息
     */
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!requestMatcher.matches(request)) {
            log.debug("Request not saved as configured RequestMatcher did not match");
            return;
        }
        DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);
        if (savedRequest.getParameterMap().containsKey(AuthorizationContacts.CLIENT_ID)){
            // 参数
            String[] values = savedRequest.getParameterValues(AuthorizationContacts.CLIENT_ID);
            // 把卷放入cookie中
            CookieUtils.set(response, AuthorizationContacts.CID, values[0], AuthorizationContacts.CID_EXPIRE.intValue());
        }

        // 判断卷是否存在
        if (isExist(savedRequest)){
            return;
        }
        // 生成缓存卷
        String ticket = String.valueOf(snowflakeIdUtils.nextId());
        // 把卷放入cookie中
        CookieUtils.set(response, AuthorizationContacts.TICKET, ticket, AuthorizationContacts.TICKET_EXPIRE.intValue());
        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(AuthorizationContacts.REDIS_TICKET_KEY + ticket);
        operations.set(savedRequest);
    }

    /**
     * 判断ticket是否存在
     */
    private boolean isExist(DefaultSavedRequest savedRequest){
        if (!savedRequest.getParameterMap().containsKey(AuthorizationContacts.TICKET)){
            return false;
        }
        // 参数
        String[] values = savedRequest.getParameterValues(AuthorizationContacts.TICKET);
        String ticket = values[0];
        // 判断ticket是否存在
        Boolean isTrue = redisTemplate.hasKey(AuthorizationContacts.REDIS_TICKET_KEY + ticket);
        return BooleanUtils.isFalse(isTrue);
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
        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(AuthorizationContacts.REDIS_TICKET_KEY + ticket);
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
        redisTemplate.delete(AuthorizationContacts.REDIS_TICKET_KEY + ticket);
    }


}
