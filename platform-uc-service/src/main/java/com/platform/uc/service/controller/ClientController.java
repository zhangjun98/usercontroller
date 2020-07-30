package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.service.service.ClientService;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 客户端控制器
 * @author hao.yan
 */
@RequestMapping("/uc/client")
@RestController
public class ClientController {

    @Resource
    private ClientService clientService;

    /**
     * 登录信息查询
     */
    @GetMapping("/{clientId}")
    public BizResponse<ClientResponse> selectClientById(@PathVariable("clientId") String clientId){
        ClientResponse client = clientService.selectClientById(clientId);
        return BizResponseUtils.success(client);
    }

    /**
     * 创建client
     */
    @PostMapping("/")
    public BizResponse<Void> save(@RequestBody ClientRequest request){
        clientService.save(request);
        return BizResponseUtils.success();
    }


}
