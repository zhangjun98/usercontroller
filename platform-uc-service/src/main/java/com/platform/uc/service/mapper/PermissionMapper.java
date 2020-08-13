package com.platform.uc.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("  SELECT * FROM uc_permission WHERE menu_id=#{id} AND state=0")
    List<Permission> findByMenuId(String id);
}
