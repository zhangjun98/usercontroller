package com.platform.uc.service.configure;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 配置
 * @author hao.yan
 */
@MapperScan(basePackages = {"com.platform.uc.service.mapper"})
@Configuration
public class MybatisConfigure {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
