package com.platform.uc.service.service;

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
     * 通过登录信息获取用户信息
     */
    public ClientResponse selectClientById(String clientId){
//        Client client = clientMapper.selectById(clientId);
        // authorization_code implicit password client_credentials refresh_token
        Client client = new Client();
        client.setId("123456");
        // 123456
        client.setSecret("$2a$10$CILXQ8p57gChZZYeNvbieeqg8iV7G65ChNMyHq1c6tyFXhQLkuo02");
        client.setRedirectUrls("http://www.baidu.com/");
        client.setGrantTypes("authorization_code,implicit,password,client_credentials,refresh_token");
        client.setAccessTokenValiditySeconds(72000);
        client.setRefreshTokenValiditySeconds(144000);
        client.setScope("READ");
        client.setAuthorities("ADMIN");
        log.info("{}", client);
        return toClientResponse(client);
    }

    private ClientResponse toClientResponse(Client client){
        ClientResponse response = new ClientResponse();
        BeanUtils.copyProperties(client, response);
        return response;
    }

}
