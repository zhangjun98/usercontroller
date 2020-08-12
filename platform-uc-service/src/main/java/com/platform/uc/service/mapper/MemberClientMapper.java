package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.MemberClient;
import com.platform.uc.service.vo.MemberRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberClientMapper extends BaseMapper<MemberClient> {
    @Select("select t1.*,t2.username FROM uc_member_client t1 LEFT JOIN uc_users t2 ON t1.mid = t2.id WHERE t1.client_id = #{clientId}")
    List<MemberClient> selectList(Long roleId);
}

