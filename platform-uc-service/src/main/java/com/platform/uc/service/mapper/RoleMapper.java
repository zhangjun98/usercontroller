package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询用户下的角色
     */
    @Select({
        "<script>",
            "select r.* from uc_role as r",
            "LEFT JOIN uc_member_role as mr ON r.id = mr.role_id ",
            "WHERE mr.mid=#{mid} and r.status=1",
        "</script>"
    })
    List<Role> selectByMid(@Param("mid") String mid);

}
