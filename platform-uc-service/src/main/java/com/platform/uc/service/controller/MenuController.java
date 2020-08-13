package com.platform.uc.service.controller;


import com.platform.uc.service.service.MenuService;
import com.platform.uc.service.vo.Menu;
import com.platform.uc.service.vo.Permission;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理
 */
@RequestMapping("/uc/menu")
@Controller
public class MenuController {

    @Resource
    private MenuService menuService;


    /**
     * 保存菜单
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public BizResponse<Integer> saveMenu(@RequestBody Menu menu){

        try {
            Integer count= menuService.saveMenu(menu);
            return BizResponseUtils.success(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }

    /**
     * 查询所有菜单目录,有序展示
     * @return
     */
    @GetMapping("/findCatalog")
    @ResponseBody
    public BizResponse<List<Menu>> findCatalog(){
        try {
            List<Menu> list= menuService.findCatalog();
            return BizResponseUtils.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return BizResponseUtils.error("999999","系统繁忙请稍后重试");
        }
    }


    /**
     * 保存子菜单
     * @return
     */
    @PostMapping("/saveSubMenu")
    @ResponseBody
    public BizResponse<Integer> saveSubMenu(@RequestBody Permission submenu){
        try {
            Integer count= menuService.saveSubMenu(submenu);
            return BizResponseUtils.success(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }


    /**
     * 修改子菜单
     * @return
     */
    @PostMapping("/updateSubMenu")
    @ResponseBody
    public BizResponse<Integer> updateSubMenu(@RequestBody Permission submenu){
        try {
            Integer count= menuService.updateSubMenu(submenu);
            return BizResponseUtils.success(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }


    /**
     * 逻辑删除子菜单
     * @return
     */
    @GetMapping("/delSubMenu")
    @ResponseBody
    public BizResponse<Integer> delSubMenu(@RequestParam String id){
        try {
            Integer count= menuService.delSubMenu(id);
            return BizResponseUtils.success(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }

    /**
     * 根据菜单的id查询对应的所有子菜单
     * @return
     */
    @GetMapping("/findSubMenu")
    @ResponseBody
    public BizResponse<List<Permission>> findSubMenu(@RequestParam Long id){
        try {
            List<Permission> list= menuService.findSubMenu(id);
            return BizResponseUtils.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }


    /**
     * 查询所有菜单 同时带上对应的子菜单
     * @return
     */
    @GetMapping("/findAll")
    @ResponseBody
    public BizResponse<List<Menu>> findAll(){

        try {
            List<Menu> list= menuService.findAll();
            return BizResponseUtils.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BizResponseUtils.error("999999","系统繁忙请稍后重试");
    }
}
