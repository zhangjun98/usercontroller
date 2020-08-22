package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.api.vo.response.ClientMemberResponse;
import com.platform.uc.service.vo.MemberClient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberClientMapper extends BaseMapper<MemberClient> {

    @Select({"<script>" +
            "select m.*, u.id as uid, u.username as username, u.email as email, u.mobile as mobile, u.enabled as enabled, mr.org_id as org_id, mr.create_date as create_date, mr.update_date as update_date \n" +
            "from uc_members as m \n" +
            "LEFT JOIN uc_users as u on u.mid = m.id \n" +
            "LEFT JOIN uc_member_client as mr on mr.mid = m.id \n" +
            "where 1=1 \n" +
            "<if test='params.clientId!=null'>",
            "AND mr.client_id = #{params.clientId} ",
            "</if>",
            "<if test='params.searchName!=null'>",
                "AND  (username like concat('%', '${params.searchName}', '%') OR email like concat('%', '${params.searchName}', '%') OR mobile like concat('%', '${params.searchName}', '%'))",
            "</if>",
    "</script>"})
    List<ClientMemberResponse> selectUsersByClientId(Page<ClientMemberResponse> page, @Param("params") QueryClientUserRequest params);
}

