package com.platform.uc.api;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.api.vo.response.UserResponse;
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
@FeignClient(value = "platform-uc-service", path = "/uc/client")
public interface RemoteClientService {

    /**
     * 通过用户名查询用户信息
     */
    @GetMapping("/{clientId}")
    BizResponse<ClientResponse> selectClientById(@PathVariable("clientId") String clientId);

    /**
     * 创建client
     */
    @PostMapping("/")
    BizResponse<Void> save(@RequestBody ClientRequest request);

}
