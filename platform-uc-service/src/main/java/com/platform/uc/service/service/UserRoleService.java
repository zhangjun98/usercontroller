package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.UserRoleMapper;
import com.platform.uc.service.vo.UserRole;
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
public class UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 保存用户角色
     * @param userRole
     */
    public int saveUserRole(UserRole userRole) {
        if (userRole==null){
            throw new RuntimeException("菜单对象为空");
        }
        userRole.setCreateDate(new Date());

        int insert = userRoleMapper.insert(userRole);
        return insert;
    }

    /**
     * 通过用户ID获取角色信息
     * @param userId
     * @return
     */
    public List<UserRole> selectByUserId(String userId){
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id", userId);
        }
        List<UserRole> listRole=  userRoleMapper.selectList(wrapper);
        return listRole;
    }

    /**
     * 通过用户ID获取角色信息
     * @param id
     * @return
     */
    public UserRole selectById(String id){
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        return userRoleMapper.selectById(wrapper);
    }

    /**
     * 更新角色
     *
     * @param userRole
     */
    public int updateUserRole(UserRole userRole) {

        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userRole.getRoleId())) {
            wrapper.eq("role_id", userRole.getRoleId());
        }

        return userRoleMapper.update(userRole, wrapper);
    }

    /**
     * 删除角色信息
     * @param id
     */
    public int deleteById(String id) {
        return userRoleMapper.deleteById(id);
    }

    /**
     * 批量删除角色信息
     * @param idList
     */
    public int batchDelete(List<String> idList) {
        for (String id: idList) {
            userRoleMapper.deleteById(id);
        }
        return 1;
    }

}

