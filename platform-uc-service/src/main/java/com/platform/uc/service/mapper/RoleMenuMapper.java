package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.api.vo.response.RoleMenuResponse;
import com.platform.uc.service.vo.RoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

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

    @Select({"<script>" +
            "select rm.*,u.name as menu_name ,u.parent_id as parentId\n" +
            "from uc_role_menu as rm \n" +
            "LEFT JOIN uc_menu as u on u.id = rm.menu_id \n" +
            "where 1=1 \n" +
            "<if test='roleId!=null'>",
            "AND rm.role_id = #{roleId}",
            "</if>",
            "</script>"})
    List<RoleMenuResponse> selectMenusByRole(@Param("roleId")String roleId);

}
