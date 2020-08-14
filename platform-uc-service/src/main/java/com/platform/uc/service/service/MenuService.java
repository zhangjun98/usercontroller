package com.platform.uc.service.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MenuRequest;
import com.platform.uc.api.vo.response.MenuResponse;
import com.platform.uc.api.vo.response.TreeMenuResponse;
import com.platform.uc.service.mapper.MenuMapper;
import com.platform.uc.service.vo.Menu;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
     */
    public void save(MenuRequest request) {
        Menu menu = BeanUtils.toT(request, Menu.class);
        if (ObjectUtils.isEmpty(menu)){
            throw new RuntimeException("菜单对象为空");
        }
        menu.setCreateDate(new Date());
        menu.setCreatorId(request.getOperator());
        // 添加排序
        if (StringUtils.isEmpty(menu.getParentId())){
            // 一级菜单 父id为0
            menu.setParentId("0");
        }
        int count = menuMapper.insert(menu);
        if (count <= 0){
            throw new BizException(UserErrorCode.MENU_INSERT_FAIL);
        }
    }

    public void modify(String id, MenuRequest request){
        Menu menu = BeanUtils.toT(request, Menu.class);
        if (ObjectUtils.isEmpty(menu)){
            throw new RuntimeException("菜单对象为空");
        }
        menu.setId(id);
        menu.setUpdateDate(new Date());
        menu.setUpdaterId(request.getOperator());
        // 添加排序
        if (StringUtils.isEmpty(menu.getParentId())){
            // 一级菜单 父id为0
            menu.setParentId("0");
        }
        int count = menuMapper.updateById(menu);
        if (count <= 0){
            throw new BizException(UserErrorCode.MENU_INSERT_FAIL);
        }
    }

    /**
     * 删除菜单
     */
    public void remove(ChangeStatusRequest request) {
        UpdateWrapper<Menu> wrapper = new UpdateWrapper<>();
        wrapper.in("id", request.getIds());
        Menu menu = new Menu();
        menu.setStatus(request.isEnable());
        menu.setUpdateDate(new Date());
        menu.setUpdaterId(request.getOperator());
        menuMapper.update(menu, wrapper);
    }

    /**
     * 根据父节点查询菜单
     */
    public BizPageResponse<MenuResponse> selectMenuByParentId(String parentId){
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("status", true);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuResponse> responses = menus.stream()
                .map(item->BeanUtils.toT(item, MenuResponse.class))
                .collect(Collectors.toList());
        return BizPageResponseUtils.success(responses);
    }

    /**
     * 树形菜单
     */
    public BizPageResponse<TreeMenuResponse> tree(){
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<>());
        List<TreeMenuResponse> menuResponses = BeanUtils.toList(menus, TreeMenuResponse.class);
        List<TreeMenuResponse> tree = buidTree(menuResponses);
        return BizPageResponseUtils.success(tree);
    }

    /**
     * 把一个List转成树
     */
    private List<TreeMenuResponse> buidTree(List<TreeMenuResponse> list){
        List<TreeMenuResponse> tree = new ArrayList<>();
        for(TreeMenuResponse node:list){
            if(node.getPid().equals("0")){
                tree.add(findChild(node,list));
            }
        }
        return tree;
    }

    private TreeMenuResponse findChild(TreeMenuResponse node, List<TreeMenuResponse> list){
        for(TreeMenuResponse n:list){
            if(n.getPid().compareTo(node.getId()) == 0){
                if(node.getChildren() == null){
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChild(n,list));
            }
        }
        return node;
    }

}
