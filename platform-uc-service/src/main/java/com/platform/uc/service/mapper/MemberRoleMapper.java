package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;

import com.platform.uc.api.vo.response.MemberRoleResponse;
import com.platform.uc.service.vo.RoleMember;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息角色
 */
@Mapper
public interface MemberRoleMapper extends BaseMapper<RoleMember> {

	@Select({"<script>" +
			"select m.*, u.id as uid, u.username as username, u.email as email, u.mobile as mobile, mr.create_date as create_date, mr.update_date as update_date \n" +
			"from uc_members as m \n" +
			"LEFT JOIN uc_users as u on u.mid = m.id \n" +
			"LEFT JOIN uc_member_role as mr on mr.mid = m.id \n" +
			"where 1=1 \n" +
			"<if test='params.roleId!=null'>",
				"AND mr.role_id = #{params.roleId}",
			"</if>",
	"</script>"})
	List<MemberRoleResponse> selectUsersByRole(Page<MemberRoleResponse> page, @Param("params") QueryRoleUserRequest params);


	@Insert({
		"<script>",
			"insert into uc_member_role(mid, role_id, create_date) values ",
			"<foreach collection='items' item='item' index='index' separator=','>",
				"(#{item.mid}, #{item.roleId}, #{item.createDate})",
			"</foreach>",
		"</script>"
	})
	int insertBatch(@Param("items") Collection<RoleMember> items);

}

