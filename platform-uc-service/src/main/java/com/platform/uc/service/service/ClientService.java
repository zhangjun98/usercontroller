package com.platform.uc.service.service;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.service.mapper.ClientMapper;
import com.platform.uc.service.vo.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务类
 * @author hao.yan
 */
@Slf4j
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    /**
     * 保存
     */
    public void save(ClientRequest request){
        Client client = new Client();
        BeanUtils.copyProperties(request, client);
        clientMapper.insert(client);
    }

    /**
     * 通过登录信息获取用户信息
     */
    public ClientResponse selectClientById(String clientId){
        Client client = clientMapper.selectById(clientId);
        log.info("{}", client);
        return toClientResponse(client);
    }

    private ClientResponse toClientResponse(Client client){
        ClientResponse response = new ClientResponse();
        BeanUtils.copyProperties(client, response);
        return response;
    }

}
