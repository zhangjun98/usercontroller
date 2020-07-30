package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.DeveloperRequest;
import com.platform.uc.api.vo.response.DeveloperResponse;
import com.platform.uc.service.service.DeveloperService;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 开发者管理
 * @author hao.yan
 */
@RequestMapping("/developer")
@RestController
public class DeveloperController {

    @Resource
    private DeveloperService developerService;

    /**
     * 通过用户名查询开发者信息
     */
    @GetMapping("/{id}")
    public BizResponse<DeveloperResponse> selectDeveloperById(@PathVariable("id") String id){
        return null;
    }

    /**
     * 创建开发者
     */
    @PostMapping("/")
    public BizResponse<Void> save(@RequestBody DeveloperRequest request){
        return null;
    }


}
