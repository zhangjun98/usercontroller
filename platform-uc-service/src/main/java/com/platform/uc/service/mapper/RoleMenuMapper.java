package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.RoleMember;
import com.platform.uc.service.vo.RoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    @Insert({
            "<script>",
            "insert into uc_role_menu(role_id, menu_id) values ",
            "<foreach collection='items' item='item' index='index' separator=','>",
                "(#{item.roleId}, #{item.menuId})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param("items") Collection<RoleMenu> items);

}
