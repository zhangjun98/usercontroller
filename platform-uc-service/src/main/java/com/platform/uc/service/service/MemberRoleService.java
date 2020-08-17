package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.request.RoleMemberRequest;
import com.platform.uc.api.vo.response.MemberRoleResponse;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.vo.RoleMember;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关系业务类
 * @author fang
 */
@Slf4j
@Service
public class MemberRoleService {

    @Resource
    private MemberRoleMapper memberRoleMapper;

    /**
     * 查询角色下的用户
     */
    public BizPageResponse<MemberRoleResponse> selectUsersByConditions(QueryRoleUserRequest request){
        Page<MemberRoleResponse> page = new Page<>();
        page.setCurrent(request.getPageNo());
        page.setSize(request.getPageSize());
        List<MemberRoleResponse> members = memberRoleMapper.selectUsersByRole(page, request);
        return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), members);
    }

    /**
     * 保存用户角色
     */
    public void save(Collection<RoleMemberRequest> roleMembers) {
        if (CollectionUtils.isEmpty(roleMembers)){
            throw new RuntimeException("菜单对象为空");
        }
        Date now = new Date();
        List<RoleMember> roleMemberList = roleMembers.stream().map(item->{
            RoleMember roleMember = BeanUtils.toT(item, RoleMember.class);
            roleMember.setCreateDate(now);
            return roleMember;
        }).collect(Collectors.toList());
        int count = memberRoleMapper.insertBatch(roleMemberList);
        if (count <= 0){
            throw new BizException(UserErrorCode.MEMBER_ROLE_DELETE_FAIL);
        }
    }

    /**
     * 批量删除角色信息
     */
    public void remove(BatchRequest request) {
        memberRoleMapper.deleteBatchIds(request.getIds());
    }

    /**
     * 删除用户下面的角色
     */
    public void removeByMid(String mid) {
        UpdateWrapper<RoleMember> wrapper = new UpdateWrapper<>();
        wrapper.eq("mid", mid);
        memberRoleMapper.delete(wrapper);
    }

}

