package com.platform.uc.adapter.configure;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * feign配置
 * @author hao.yan
 */
@Configuration
@EnableFeignClients(basePackages = {"com.platform.uc.api"})
public class FeignConfigure {
/**
 * 配置 服务间调用的配置，对应的是 api中的 feignclient
 */
}
