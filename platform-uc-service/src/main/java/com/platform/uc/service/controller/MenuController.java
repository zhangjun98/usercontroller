package com.platform.uc.service.controller;


import com.platform.uc.service.service.MenuService;
import com.platform.uc.service.vo.Menu;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 */
@RequestMapping("/menu")
@Controller
public class MenuController {


    @Autowired
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

}
