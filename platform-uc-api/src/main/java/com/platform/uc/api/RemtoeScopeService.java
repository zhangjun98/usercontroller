package com.platform.uc.api;

import com.platform.uc.api.vo.response.ScopeResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

/**
 * 远程范围信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/scope")
public interface RemtoeScopeService {


    @PostMapping("/names")
    BizPageResponse<ScopeResponse> searchByScopeNames(
            @RequestBody Collection<String> scopeNames
    );

}
