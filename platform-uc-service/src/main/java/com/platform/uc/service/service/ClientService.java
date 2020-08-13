package com.platform.uc.service.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.response.ClientResponse;
import com.platform.uc.service.mapper.ClientMapper;
import com.platform.uc.service.mapper.MemberClientMapper;
import com.platform.uc.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户业务类
 * @author hao.yan
 */
@Slf4j
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private MemberClientMapper memberClientMapper;
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
        if (!StringUtils.isEmpty(client.getAdditionalInformation())){
            response.setAdditionalInformation(JSON.parseObject(client.getAdditionalInformation(), new TypeReference<Map<String, Object>>(){}));
        }
        return response;
    }



    public void update(Client client)
    {
        clientMapper.updateById(client);
    }

    public Client selectBean(Long id)
    {
        return clientMapper.selectById(id);
    }

    public List<Client> selectList(String name)
    {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",0);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(name))
        {
            queryWrapper.like("name", name);
        }
        return clientMapper.selectList(queryWrapper);
    }

    public IPage<Client> selectList(String name, Integer pageNum, Integer pageSize)
    {
        Page<Client> dataElementPage = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(name))
        {
            queryWrapper.like("name", name);
        }
        return clientMapper.selectPage(dataElementPage,queryWrapper);
    }

    public List<MemberClient> selectClientUsers(Long clientId)
    {
        return memberClientMapper.selectList(clientId);
    }

}
