package com.platform.uc.service.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.MenuMapper;
import com.platform.uc.service.mapper.PermissionMapper;
import com.platform.uc.service.vo.Menu;
import com.platform.uc.service.vo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜单管理服务类
 */
@Service
@Slf4j
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    public Integer saveMenu(Menu menu) {
        if (menu==null){
            throw new RuntimeException("菜单对象为空");
        }
        menu.setCreateDate(new Date());
        menu.setState(0L);//状态 0表示正常
        //添加排序
        if (menu.getParentId()==null){
            menu.setParentId(0L);//一级菜单 父id为0
        }
            //不是一级菜单
            QueryWrapper<Menu> queryWapper=new QueryWrapper<Menu>();
            queryWapper.eq("parent_id",menu.getParentId());
            Integer count = menuMapper.selectCount(queryWapper);
            menu.setSeq((long) (count+1));//设置排序

        int insert = menuMapper.insert(menu);
        return insert;
    }

    /**
     * 查询所有的菜单目录
     * @return
     */
    public List<Menu> findCatalog() {
        //查询所有的菜单
        List<Menu> list = menuMapper.selectList(null);
        if (list!=null && list.size()>0){

            //所有结果放在map中
            HashMap<Long, Object> allMap = new HashMap<>(list.size());
            for (Menu menu : list) {
                allMap.put(menu.getId(),menu);
            }

            //遍历所有的菜单,把每个菜单的子菜单保存起来
            for (int i = 0; i < list.size(); i++) {
                Menu menu = (Menu) allMap.get(list.get(i).getParentId());
                if (menu!=null){
                    if (menu.getList()==null){
                        menu.setList(new ArrayList<Menu>());
                    }

                    menu.getList().add(list.get(i));
                    Collections.sort(menu.getList(),new SeqComparator());
                    allMap.put(list.get(i).getParentId(),menu);
                }
            }

            //得到一级分类
            ArrayList<Menu> menus = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getParentId()==0L){
                    menus.add(list.get(i));
                }
            }
            //排序
            Collections.sort(menus,new SeqComparator());
            return menus;
        }

        return null;
    }


    /**
     * 保存子菜单
     * @param submenu
     * @return
     */
    public Integer saveSubMenu(Permission submenu) {
        if (submenu==null || submenu.getMenuId()==null ||menuMapper.selectById(submenu.getMenuId())==null){
            throw new RuntimeException("保存子菜单时参数错误");
        }
        submenu.setState(0);
        submenu.setCreateDate(new Date());
        int insert = permissionMapper.insert(submenu);
        return insert;
    }

    /**
     * 修改子菜单
     * @param submenu
     * @return
     */
    public Integer updateSubMenu(Permission submenu) {
        if (submenu==null || submenu.getId()==null || submenu.getMenuId()==null ||menuMapper.selectById(submenu.getMenuId())==null){
            throw new RuntimeException("修改子菜单时参数错误");
        }
        submenu.setUpdateDate(new Date());
        int count = permissionMapper.updateById(submenu);
        return count;
    }

    /**
     * 根据id逻辑删除子菜单
     * @param id
     * @return
     */
    public Integer delSubMenu(String id) {

        if (id==null){
            throw new RuntimeException("删除子菜单时id为空");
        }
        Permission submenu = permissionMapper.selectById(id);
        if (submenu==null){
            throw new RuntimeException("删除子菜单时没有对应id的子菜单");
        }

        submenu.setState(9);
        int count = permissionMapper.updateById(submenu);
        return count;
    }

    /**
     * 根据菜单id查询所有的子菜单
     * @param id 菜单的id
     * @return
     */
    public List<Permission> findSubMenu(Long id) {
        if (id==null){
            throw new RuntimeException("根据菜单id查询所有的子菜单时菜单id为空");
        }
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",id).eq("state",0);
        List<Permission> submenus = permissionMapper.selectList(queryWrapper);
        return submenus;
    }

    /**
     * 查询所有菜单 同时带上对应的子菜单
     * @return
     */
    @SuppressWarnings("all")
    public List<Menu> findAll() {

        //先一对多查询所有菜单 并对上 相应的子菜单
        List<Menu> list= menuMapper.findAll();
        if (list!=null && list.size()>0){

            //所有结果放在map中
            HashMap<Long, Object> allMap = new HashMap<>(list.size());
            for (Menu menu : list) {
                allMap.put(menu.getId(),menu);
            }

            //遍历所有的菜单,把每个菜单的子菜单保存起来
            for (int i = 0; i < list.size(); i++) {
                Menu menu = (Menu) allMap.get(list.get(i).getParentId());
                if (menu!=null){
                    if (menu.getList()==null){
                        menu.setList(new ArrayList<Menu>());
                    }

                    menu.getList().add(list.get(i));
                    Collections.sort(menu.getList(),new SeqComparator());
                    allMap.put(list.get(i).getParentId(),menu);
                }
            }

            //得到一级分类
            ArrayList<Menu> menus = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getParentId()==0L){
                    menus.add(list.get(i));
                }
            }
            //排序
            Collections.sort(menus,new SeqComparator());
            return menus;
        }

        return null;
    }
}
