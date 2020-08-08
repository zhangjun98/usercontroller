package com.platform.uc.adapter.controller;

import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用管理
 * @author hao.yan
 */
@RequestMapping
@RestController("/client")
public class ClientController {

    /**
     *
     */
    @GetMapping("/{id}")
    public BizResponse<Void> detail(@PathVariable(value = "id") String id){

        return BizResponseUtils.success();
    }

}
