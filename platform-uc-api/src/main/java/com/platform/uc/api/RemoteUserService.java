package com.platform.uc.api;

import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/user")
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     */
    @PostMapping("/account")
    BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName);

}
