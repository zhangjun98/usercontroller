package com.ztkj.platform.sc.adapter.configure;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * feign 配置
 * @author hao.yan
 */
@EnableFeignClients(basePackages = {"com.ztkj.platform.dc.api"})
@Configuration
public class FeignConfigure {



}
