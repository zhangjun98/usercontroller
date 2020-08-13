package com.platform.uc.api;


import com.platform.uc.api.vo.request.MenuRequest;
import com.platform.uc.api.vo.request.PermissionRequest;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程菜单的信息接口
 */
@FeignClient(value = "platform-uc-service", path = "/menu")
public interface RemoteMenuService {

    /**
     * 保存菜单
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public BizResponse<Integer> saveMenu(@RequestBody MenuRequest menu);

    /**
     * 查询所有菜单目录,有序展示
     * @return
     */
    @GetMapping("/findCatalog")
    @ResponseBody
    public BizResponse<List<MenuRequest>> findCatalog();


    /**
     * 保存子菜单
     * @return
     */
    @PostMapping("/saveSubMenu")
    @ResponseBody
    public BizResponse<Integer> saveSubMenu(@RequestBody PermissionRequest submenu);


    /**
     * 修改子菜单
     * @return
     */
    @PostMapping("/updateSubMenu")
    @ResponseBody
    public BizResponse<Integer> updateSubMenu(@RequestBody PermissionRequest submenu);


    /**
     * 逻辑删除子菜单
     * @return
     */
    @GetMapping("/delSubMenu")
    @ResponseBody
    public BizResponse<Integer> delSubMenu(@RequestParam String id);


    /**
     * 根据菜单的id查询对应的所有子菜单
     * @return
     */
    @GetMapping("/findSubMenu")
    @ResponseBody
    public BizResponse<List<PermissionRequest>> findSubMenu(@RequestParam Long id);

    /**
     * 查询所有菜单 同时带上对应的子菜单
     * @return
     */
    @GetMapping("/findAll")
    @ResponseBody
    public BizResponse<List<MenuRequest>> findAll();
}
