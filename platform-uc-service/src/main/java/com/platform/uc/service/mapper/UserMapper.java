package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.api.vo.request.QueryMemberRequest;
import com.platform.uc.service.vo.MemberDetail;
import com.platform.uc.service.vo.User;
import com.platform.uc.service.vo.UserDetail;
import org.apache.ibatis.annotations.*;

/**
 * 账户信息mapper
 * @author hao.yan
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select({"<script>" +
        "SELECT m.*, u.id as uid, u.username as username, u.email as email, u.mobile as mobile, u.password as password, ",
            "u.enabled as enabled, u.account_expired as account_expired from uc_members as m ",
        "LEFT JOIN uc_users as u ON u.mid = m.id ",
        "WHERE 1=1",
        "<if test='params.id!=null'>",
            "and m.id=#{params.id} ",
        "</if>",
        "<if test='params.searchName!=null'>",
            "AND  (username like concat('%', '${params.searchName}', '%') OR email like concat('%', '${params.searchName}', '%') OR mobile like concat('%', '${params.searchName}', '%'))",
        "</if>",
    "</script>"})
    UserDetail selectOne(@Param("params") QueryMemberRequest params);

}
