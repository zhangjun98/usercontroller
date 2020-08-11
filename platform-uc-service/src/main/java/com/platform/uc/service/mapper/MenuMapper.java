package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {



    @Select("SELECT * FROM uc_menu WHERE state=0")
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
            @Result(property = "updaterId",column = "updaterId"),
            @Result(property = "submenuList",column = "id",
                        javaType = List.class,
                    many = @Many(select = "com.platform.uc.service.mapper.SubMenuMapper.findByMenuId")
            )
    })
    List<Menu> findAll();
}
