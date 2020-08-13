package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.vo.MemberRole;
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
     * 保存用户角色
     * @param memberRole
     */
    public int saveMemberRole(MemberRole memberRole) {
        if (memberRole ==null){
            throw new RuntimeException("菜单对象为空");
        }
        memberRole.setCreateDate(new Date());

        int insert = memberRoleMapper.insert(memberRole);
        return insert;
    }

    /**
     * 通过用户ID获取角色信息
     * @param mid
     * @return
     */
    public List<MemberRole> selectByUserId(String mid){
        QueryWrapper<MemberRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(mid)) {
            wrapper.eq("mid", mid);
        }
        List<MemberRole> listRole=  memberRoleMapper.selectList(wrapper);
        return listRole;
    }

    /**
     * 通过用户ID获取角色信息
     * @param id
     * @return
     */
    public MemberRole selectById(String id){
        QueryWrapper<MemberRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        return memberRoleMapper.selectById(wrapper);
    }

    /**
     * 更新角色
     *
     * @param memberRole
     */
    public int updateUserRole(MemberRole memberRole) {

        QueryWrapper<MemberRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(memberRole.getRoleId())) {
            wrapper.eq("role_id", memberRole.getRoleId());
        }

        return memberRoleMapper.update(memberRole, wrapper);
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

