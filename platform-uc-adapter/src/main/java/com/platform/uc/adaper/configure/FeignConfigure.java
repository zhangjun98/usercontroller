package com.platform.uc.adaper.configure;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * feign配置
 * @author hao.yan
 */
@Configuration
@EnableFeignClients(basePackages = {"com.platform.uc.api"})
public class FeignConfigure {

}
