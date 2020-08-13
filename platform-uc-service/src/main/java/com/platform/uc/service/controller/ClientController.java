package com.platform.uc.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.service.service.ClientService;
import com.platform.uc.service.vo.Client;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 客户端控制器
 * @author hao.yan
 */
@RequestMapping("/client")
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
    
    //应用修改
    @PutMapping("/updateClient") @ResponseBody public BizResponse<String> updateClient(@RequestBody Client client)
    {
        if (StringUtils.isEmpty(client.getName()))
        {
            return BizResponseUtils.error("999999", "应用名不能为空");
        }
        if (client.getId() == null)
        {
            return BizResponseUtils.error("999999", "主键不能为空");
        }
        try
        {
            clientService.update(client);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
        }
        return BizResponseUtils.success("操作成功");
    }

    //应用查看回显
    @GetMapping("/selectClient/{id}") @ResponseBody public BizResponse<Client> selectClient(@PathVariable Long id)
    {
        if (id == null)
        {
            return BizResponseUtils.error("999999", "主键不能为空");
        }
        Client client = clientService.selectBean(id);
        return BizResponseUtils.success(client);
    }

    //应用列表
    @GetMapping("/selectClientList/{name}") @ResponseBody public BizResponse<IPage<Client>> selectRoleList(@PathVariable String name, @PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        IPage<Client> ucRoles = clientService.selectList(name, pageNum, pageSize);
        return BizResponseUtils.success(ucRoles);
    }

    //应用删除
    @DeleteMapping("/deleteClient/{id}") @ResponseBody public BizResponse<String> deleteClient(@PathVariable String id)
    {
        if (id == null)
        {
            return BizResponseUtils.error("999999", "主键不能为空");
        }
        Client client = new Client();
        client.setId(id);
        client.setState(9);
        clientService.update(client);
        return BizResponseUtils.success("操作成功");
    }

}
