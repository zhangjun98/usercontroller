package com.platform.uc.api;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.request.Org;
import com.platform.uc.api.vo.response.ClientResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/org")
public interface RemoteOrgService
{

    /**
     * 通过用户名查询用户信息
     */
    @PostMapping("save")
    public BizResponse<String> save(@RequestBody Org org);



}
