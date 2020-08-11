package com.platform.uc.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.Submenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubMenuMapper extends BaseMapper<Submenu> {

    @Select("  SELECT * FROM uc_submenu WHERE menu_id=#{id} AND state=0")
    List<Submenu> findByMenuId(String id);
}
