package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MenuParentRequest;
import com.platform.uc.api.vo.request.MenuRequest;
import com.platform.uc.api.vo.response.MenuDetailResponse;
import com.platform.uc.api.vo.response.MenuResponse;
import com.platform.uc.api.vo.response.TreeMenuResponse;
import com.platform.uc.service.service.MenuService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单管理
 */
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    public BizResponse<Void> save(@RequestBody MenuRequest request){
        menuService.save(request);
        return BizResponseUtils.success();
    }

    /**
     * 更新菜单
     */
    @PutMapping("/{id}")
    public BizResponse<Void> modify(@PathVariable String id, @RequestBody MenuRequest request){
        menuService.modify(id, request);
        return BizResponseUtils.success();
    }


    /**
     * 查询详情
     */
    @GetMapping("/{id}/detail")
    public BizResponse<MenuDetailResponse> modify(@PathVariable String id){
        MenuDetailResponse detailResponse = menuService.detail(id);
        return BizResponseUtils.success(detailResponse);
    }

    /**
     * 删除子菜单
     */
    @PostMapping("/remove")
    public BizResponse<Void> remove(@RequestBody ChangeStatusRequest request){
        menuService.remove(request);
        return BizResponseUtils.success();
    }

    /**
     * 查询菜单
     */
    @PostMapping("/parent")
    public BizPageResponse<MenuResponse> selectMenuByParentId(@RequestBody MenuParentRequest request){
       return menuService.selectMenuByParentId(request);
    }

    /**
     * 树形菜单
     */
    @PostMapping("/tree")
    public BizPageResponse<TreeMenuResponse> tree(){
        return menuService.tree();
    }

}
