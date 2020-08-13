package com.platform.uc.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.api.vo.response.MemberClientResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/client")
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

    @PutMapping("/updateClient") @ResponseBody BizResponse<String> updateClient(@RequestBody ClientRequest client);

    @GetMapping("/selectClient/{id}") @ResponseBody BizResponse<ClientResponse> selectClient(@PathVariable Long id);

    @GetMapping("/selectClientList/{name}") @ResponseBody BizResponse<IPage<ClientResponse>> selectRoleList(@PathVariable String name, @PathVariable Integer pageNum, @PathVariable Integer pageSize);

    @DeleteMapping("/deleteClient/{id}") @ResponseBody BizResponse<String> deleteClient(@PathVariable String id);

    @GetMapping("/selectClientUsers/{clientId}") @ResponseBody BizResponse<List<MemberClientResponse>> selectClientUsers(@PathVariable Long clientId);

}
