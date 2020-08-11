package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.UcMemberRole;
import com.platform.uc.service.vo.UcRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UcRoleMapper extends BaseMapper<UcRole>
{

	@Select("select t1.*,t2.username FROM uc_member_role t1 LEFT JOIN uc_users t2 ON t1.mid = t2.id WHERE t1.role_id = #{roleId}")
	List<UcMemberRole> selectList(Long roleId);
}
