package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * 账户信息mapper
 * @author hao.yan
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from uc_users where mid = #{mid}")
    @Results(id = "User",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "mobile",column = "mobile"),
            @Result(property = "password",column = "password"),
            @Result(property = "member",column = "mid", one = @One(select="com.platform.uc.service.mapper.MemberMapper.selectById",fetchType= FetchType.EAGER))
    })
    User selectByMid(@Param("mid")String mid);

}
