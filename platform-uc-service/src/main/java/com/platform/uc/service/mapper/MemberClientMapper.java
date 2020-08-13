package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.service.vo.MemberClient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberClientMapper extends BaseMapper<MemberClient> {

    @Select("SELECT t1.*,t2.username,t2.create_time as createTime,t2.email,t2.mobile,t3.`code`,t3.description,t3.`name`,t4.org_code as orgCode,t4.org_name as orgName FROM "
            + "uc_member_client t1" + " LEFT JOIN uc_users t2 ON t1.mid = t2.mid" + " LEFT JOIN uc_client t3 ON t1.client_id = t3.id "
            + " LEFT JOIN uc_org t4 ON t3.org_id = t3.id WHERE t1.client_id = #{clientId}")
    List<MemberClient> selectList(Page<MemberClient> page, @Param("clientId") String clientId);
}

