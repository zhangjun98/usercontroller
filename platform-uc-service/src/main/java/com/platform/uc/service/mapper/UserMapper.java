package com.platform.uc.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.uc.service.vo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息mapper
 * @author hao.yan
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
