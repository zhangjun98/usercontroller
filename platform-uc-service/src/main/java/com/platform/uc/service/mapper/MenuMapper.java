package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.api.vo.request.MenuParentRequest;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.service.vo.Menu;
import com.platform.uc.service.vo.MenuDetail;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
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
                    "and mr.mid=#{params.mid} ",
                    "</if>",
                    "<if test='params.pids!=null'>",
                        "and m.parent_id in ",
                        "<foreach collection='params.pids' open='(' item='pid' separator=',' close=')'>",
                            "#{pid}",
                        "</foreach>",
                    "</if>",
                    "<if test='params.type!=null'>",
                        "and m.type=#{params.type} ",
                    "</if>",
                    "<if test='params.searchName!=null'>",
                        "and m.name like concat('%', '${params.searchName}', '%') ",
                    "</if>",
                    "<if test='params.status!=null'>",
                        "and m.status=#{params.status} ",
                    "</if>",
                    "<if test='params.roleId!=null'>",
                        "and mr.role_id=#{params.roleId} ",
                    "</if>",
                    "GROUP BY m.id",
            "</script>"})
    List<Menu> selectByMid(@Param("params") MenuParentRequest params);


    @Select({
            "<script>",
            "select m.*, pm.`name` as parent_name from uc_menu as m ",
                    "LEFT JOIN uc_menu as pm on m.parent_id = pm.id ",
                    "where m.id=#{id}",
            "</script>"
    })
    MenuDetail selectByMenuId(@Param("id") Serializable id);
}
