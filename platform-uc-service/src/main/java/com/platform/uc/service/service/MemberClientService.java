package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.RoleMemberResponse;
import com.platform.uc.service.mapper.MemberClientMapper;
import com.platform.uc.service.vo.MemberClient;

import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberClientService {

    @Resource
    private MemberClientMapper memberClientMapper;

    /**
     * 保存用户平台
     */
    public void save(MemberClientRequest request) {
        MemberClient memberClient = BeanUtils.toT(request, MemberClient.class);
        if (memberClient ==null){
            throw new RuntimeException("对象为空");
        }
        memberClient.setCreateDate(new Date());
        int count = memberClientMapper.insert(memberClient);
        if (count <= 0){
            throw new BizException(UserErrorCode.MEMBER_CLIENT_INSERT_FAIL);
        }
    }

    /**
     * 通过用户ID获取平台信息
     * @param mid
     * @return
     */
    public List<MemberClient> selectByUserId(String mid){
        QueryWrapper<MemberClient> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(mid)) {
            wrapper.eq("mid", mid);
        }
        List<MemberClient> listRole=  memberClientMapper.selectList(wrapper);
        return listRole;
    }

    /**
     * 通过用户ID获取平台信息
     * @param id
     * @return
     */
    public MemberClient selectById(String id){
        QueryWrapper<MemberClient> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        return memberClientMapper.selectById(wrapper);
    }


    /**
     * 批量删除或恢复
     */
    public void changeStatus(ChangeStatusRequest request) {
        UpdateWrapper<MemberClient> wrapper = new UpdateWrapper<>();
        wrapper.in("id", request.getIds());
        MemberClient memberClient = new MemberClient();
        memberClient.setDeleted(request.isEnable());
        memberClient.setUpdateDate(new Date());
        int count = memberClientMapper.update(memberClient, wrapper);
        if (count <= 0){
            throw new BizException(UserErrorCode.MEMBER_CLIENT_DELETE_FAIL);
        }
    }

    /**
     * 批量删除平台信息
     */
    public void batchDelete(BatchRequest request) {
        UpdateWrapper<MemberClient> wrapper = new UpdateWrapper<>();
        wrapper.in("id", request.getIds());
        int count = memberClientMapper.delete(wrapper);
        if (count <= 0){
            throw new BizException(UserErrorCode.MEMBER_CLIENT_DELETE_FAIL);
        }
    }

    /**
     * 查询应用下的用户
     */
    public BizPageResponse<RoleMemberResponse> selectUsersByClientId(QueryClientUserRequest request){
        Page<RoleMemberResponse> page = new Page<>();
        page.setCurrent(request.getPageNo());
        page.setSize(request.getPageSize());
        List<RoleMemberResponse> members = memberClientMapper.selectUsersByClientId(page, request);
        return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), members);
    }

}
