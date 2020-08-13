package com.platform.uc.service.controller;

import com.platform.uc.api.vo.response.ScopeResponse;
import com.platform.uc.service.service.ScopeService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.CommonErrorCode;
import com.ztkj.framework.response.exception.BizException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 客户端控制器
 * @author hao.yan
 */
@RequestMapping("/scope")
@RestController
public class ScopeController {

    @Resource
    private ScopeService scopeService;

    /**
     * 登录信息查询
     */
    @PostMapping("/names")
    public BizPageResponse<ScopeResponse> searchByScopeNames(
        @RequestBody Collection<String> scopeNames
    ){
        if (CollectionUtils.isEmpty(scopeNames)){
            throw new BizException(CommonErrorCode.PARAMS_ERROR);
        }
        return scopeService.searchByNames(scopeNames);
    }

}
