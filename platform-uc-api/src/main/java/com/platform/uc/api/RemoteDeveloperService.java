package com.platform.uc.api;

import com.platform.uc.api.vo.request.DeveloperRequest;
import com.platform.uc.api.vo.response.DeveloperResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程开发者信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/developer")
public interface RemoteDeveloperService {

    /**
     * 通过用户名查询开发者信息
     */
    @GetMapping("/{id}")
    BizResponse<DeveloperResponse> selectDeveloperById(@PathVariable("id") String id);

    /**
     * 创建开发者
     */
    @PostMapping("/")
    BizResponse<Void> save(@RequestBody DeveloperRequest request);

}
