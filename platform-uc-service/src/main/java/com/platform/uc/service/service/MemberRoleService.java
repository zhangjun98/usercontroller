package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.vo.Role;
import com.platform.uc.service.vo.RoleMember;
import com.platform.uc.service.vo.RoleMemberVo;
import com.ztkj.framework.response.core.BizPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public BizPageResponse<UserResponse> selectUsersByConditions(QueryRoleUserRequest request){

        Page<RoleMemberVo> page = new Page<>();
        page.setCurrent(request.getPageNo());
        page.setSize(request.getPageSize());
        List<RoleMemberVo> members = memberRoleMapper.selectUsersByRole(page, request);
        log.info("page = {}",page);
        log.info("members = {}",members);
        return null;
    }



    /**
     * 保存用户角色
     * @param roleMember
     */
    public int saveMemberRole(RoleMember roleMember) {
        if (roleMember ==null){
            throw new RuntimeException("菜单对象为空");
        }
        roleMember.setCreateDate(new Date());

        int insert = memberRoleMapper.insert(roleMember);
        return insert;
    }

    /**
     * 通过用户ID获取角色信息
     * @param mid
     * @return
     */
    public List<RoleMember> selectByUserId(String mid){
        QueryWrapper<RoleMember> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(mid)) {
            wrapper.eq("mid", mid);
        }
        List<RoleMember> listRole=  memberRoleMapper.selectList(wrapper);
        return listRole;
    }

    /**
     * 通过用户ID获取角色信息
     * @param id
     * @return
     */
    public RoleMember selectById(String id){
        QueryWrapper<RoleMember> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        return memberRoleMapper.selectById(wrapper);
    }

    /**
     * 更新角色
     *
     * @param roleMember
     */
    public int updateUserRole(RoleMember roleMember) {

        QueryWrapper<RoleMember> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleMember.getRoleId())) {
            wrapper.eq("role_id", roleMember.getRoleId());
        }

        return memberRoleMapper.update(roleMember, wrapper);
    }

    /**
     * 删除角色信息
     * @param id
     */
    public int deleteById(String id) {
        return memberRoleMapper.deleteById(id);
    }

    /**
     * 批量删除角色信息
     * @param idList
     */
    public int batchDelete(List<String> idList) {
        for (String id: idList) {
            memberRoleMapper.deleteById(id);
        }
        return 1;
    }

}

