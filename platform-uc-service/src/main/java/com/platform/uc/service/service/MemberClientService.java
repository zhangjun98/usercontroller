package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.vo.MemberRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberClientService {


    @Resource
    private MemberRoleMapper memberRoleMapper;

    /**
     * 保存用户平台
     * @param memberRole
     */
    public int saveUserRole(MemberRole memberRole) {
        if (memberRole ==null){
            throw new RuntimeException("菜单对象为空");
        }
        memberRole.setCreateDate(new Date());

        int insert = memberRoleMapper.insert(memberRole);
        return insert;
    }

    /**
     * 通过用户ID获取平台信息
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
     * 通过用户ID获取平台信息
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
     * 更新平台
     *
     * @param memberRole
     */
    public int updateUserRole(MemberRole memberRole) {

        QueryWrapper<MemberRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(memberRole.getRoleId())) {
            wrapper.eq("client_id", memberRole.getRoleId());
        }

        return memberRoleMapper.update(memberRole, wrapper);
    }

    /**
     * 删除平台信息
     * @param id
     */
    public int deleteById(String id) {
        return memberRoleMapper.deleteById(id);
    }

    /**
     * 批量删除平台信息
     * @param idList
     */
    public int batchDelete(List<String> idList) {
        for (String id: idList) {
            memberRoleMapper.deleteById(id);
        }
        return 1;
    }

}
