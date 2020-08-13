package com.platform.uc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户中心底层
 * @author hao.yan
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UcServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcServiceApplication.class, args);
    }

}
