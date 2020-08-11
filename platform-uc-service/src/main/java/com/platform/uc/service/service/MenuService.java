package com.platform.uc.service.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.MenuMapper;
import com.platform.uc.service.vo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
