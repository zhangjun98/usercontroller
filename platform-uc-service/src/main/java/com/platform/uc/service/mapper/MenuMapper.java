package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.api.vo.request.MenuParentRequest;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.service.vo.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    @Select("SELECT * FROM uc_menu WHERE status=0")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "moduleId",column = "module_id"),
            @Result(property = "name",column = "name"),
            @Result(property = "code",column = "code"),
            @Result(property = "icon",column = "icon"),
            @Result(property = "url",column = "url"),
            @Result(property = "seq",column = "seq"),
            @Result(property = "createDate",column = "createDate"),
            @Result(property = "creatorId",column = "creatorId"),
            @Result(property = "updateDate",column = "updateDate"),
            @Result(property = "updaterId",column = "updaterId")
    })
    List<Menu> findAll();

    @Select({"<script>",
            "select m.* from uc_menu as m " ,
                    "LEFT JOIN uc_role_menu as rm on m.id = rm.menu_id " ,
                    "LEFT JOIN uc_member_role as mr on mr.role_id = rm.role_id " ,
                    "where 1=1 ",
                    "<if test='params.mid!=null'>",
                        "mr.mid=#{params.mid} ",
                    "</if>",
                    "<if test='params.type!=null'>",
                        "m.type=#{params.type} ",
                    "</if>",
                    "<if test='params.searchName!=null'>",
                        "m.name like concat('%', '${params.searchName}', '%') ",
                    "</if>",
                    "<if test='params.status!=null'>",
                        "m.status=#{params.status} ",
                    "</if>",
                    "GROUP BY m.id",
            "</script>"})
    List<Menu> selectByMid(@Param("params") MenuParentRequest params);

}
