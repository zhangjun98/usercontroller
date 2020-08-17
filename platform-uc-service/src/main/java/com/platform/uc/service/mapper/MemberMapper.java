package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.api.vo.request.QueryMemberRequest;
import com.platform.uc.api.vo.response.ClientMemberResponse;
import com.platform.uc.service.vo.Member;
import com.platform.uc.service.vo.MemberDetail;
import com.platform.uc.service.vo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户信息mapper
 * @author hao.yan
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {


    @Select({"<script>" +
            "SELECT m.*, u.username as username, u.email as email, u.mobile as mobile from uc_members as m ",
            "LEFT JOIN uc_users as u ON u.mid = m.id ",
            "WHERE 1=1",
            "<if test='params.id!=null'>",
            "and m.id=#{params.id} ",
            "</if>",
            "<if test='params.searchName!=null'>",
            "AND  (username like concat('%', '${params.searchName}', '%') OR email like concat('%', '${params.searchName}', '%') OR mobile like concat('%', '${params.searchName}', '%'))",
            "</if>",
            "</script>"})
    MemberDetail selectOne(@Param("params") QueryMemberRequest params);

    @Select({"<script>" +
            "SELECT m.*, u.username as username, u.email as email, u.mobile as mobile from uc_members as m ",
            "LEFT JOIN uc_users as u ON u.mid = m.id ",
            "WHERE 1=1",
            "<if test='params.mid!=null'>",
             "and m.id=#{params.id} ",
            "</if>",
            "<if test='params.searchName!=null'>",
            "AND  (username like concat('%', '${params.searchName}', '%') OR email like concat('%', '${params.searchName}', '%') OR mobile like concat('%', '${params.searchName}', '%'))",
            "</if>",
            "</script>"})
    List<MemberDetail> selectByConditions(Page<MemberDetail> page, @Param("params") QueryMemberRequest params);

}
