package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.api.vo.response.RoleMenuResponse;
import com.platform.uc.service.vo.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 机构mapper
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {

    @Select({"<script>" +
            "select org.*,m.nickname as nickName ,u.avatar as avatar, u.phone as phone, u.email as mail\n" +
            "from uc_org as org \n" +
            "LEFT JOIN uc_members as m on m.id = org.mid \n" +
            "LEFT JOIN uc_users as u on u.mid = org.mid \n" +
            "where 1=1 AND org.status = 0\n" +
            "</script>"})
    List<OrganizationResponse> selectOrgList();

}
