package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MenuRequest;
import com.platform.uc.api.vo.response.MenuResponse;
import com.platform.uc.service.service.MenuService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单管理
 */
@RequestMapping("/menu")
@Controller
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    @ResponseBody
    public BizResponse<Void> save(@RequestBody MenuRequest request){
        menuService.save(request);
        return BizResponseUtils.success();
    }

    /**
     * 更新菜单
     */
    @PutMapping("/{id}")
    @ResponseBody
    public BizResponse<Void> modify(@PathVariable String id, @RequestBody MenuRequest request){
        menuService.modify(id, request);
        return BizResponseUtils.success();
    }

    /**
     * 删除子菜单
     */
    @PostMapping("/remove")
    @ResponseBody
    public BizResponse<Void> remove(@RequestBody ChangeStatusRequest request){
        menuService.remove(request);
        return BizResponseUtils.success();
    }


    /**
     * 查询所有菜单 同时带上对应的子菜单
     */
    @GetMapping("/{pid}")
    @ResponseBody
    public BizPageResponse<MenuResponse> selectMenuByParentId(@PathVariable("pid") String parentId){
       return menuService.selectMenuByParentId(parentId);
    }

}
