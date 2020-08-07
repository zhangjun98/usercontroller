package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.api.vo.response.ScopeResponse;
import com.platform.uc.service.mapper.ClientMapper;
import com.platform.uc.service.mapper.ScopeMapper;
import com.platform.uc.service.utils.BeanCloneUtils;
import com.platform.uc.service.vo.Client;
import com.platform.uc.service.vo.Scope;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 范围业务
 * @author hao.yan
 */
@Slf4j
@Service
public class ScopeService {

    @Resource
    private ScopeMapper scopeMapper;

    public BizPageResponse<ScopeResponse> searchByNames(Collection<String> scopeNames){
        QueryWrapper<Scope> wrapper = new QueryWrapper<>();
        wrapper.in("name", scopeNames);
        List<Scope> scopes = scopeMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(scopes)){
            return BizPageResponseUtils.success(new ArrayList<>());
        }
        return BizPageResponseUtils.success(BeanCloneUtils.convert(scopes, ScopeResponse.class));
    }

}
