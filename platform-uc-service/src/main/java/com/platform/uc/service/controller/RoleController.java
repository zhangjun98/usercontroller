package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.BindMenuRequest;
import com.platform.uc.api.vo.request.QueryRoleRequest;
import com.platform.uc.api.vo.request.RoleRequest;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.service.service.RoleService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource private RoleService roleService;

	/**
	 * 查询角色列表
	 */
	@PostMapping("/query")
	public BizPageResponse<RoleResponse> selectByConditions(@RequestBody QueryRoleRequest request){
		return roleService.selectByConditions(request);
	}

	/**
	 * 角色新增
	 */
	@PostMapping("/")
	public BizResponse<Void> save(@RequestBody RoleRequest request) {
		roleService.insert(request);
		return BizResponseUtils.success();
	}

	/**
	 * 角色修改
	 */
	@PutMapping("/{id}")
	public BizResponse<Void> modify(@PathVariable String id, @RequestBody RoleRequest request) {
		roleService.update(id, request);
		return BizResponseUtils.success();
	}

	/**
	 * 角色详情
	 */
	@GetMapping("/{id}")
	public BizResponse<RoleResponse> detail(@PathVariable String id) {
		RoleResponse role = roleService.detail(id);
		return BizResponseUtils.success(role);
	}

	/**
	 * 移除
	 */
	@PostMapping("/remove")
	public BizResponse<Void> remove(@RequestBody BatchRequest request){
		roleService.changeStatus(request, 9);
		return BizResponseUtils.success();
	}

	/**
	 * 权限配置
	 */
	@PostMapping("/bind/menus")
	public BizResponse<Void> bindMenus(@RequestBody Collection<BindMenuRequest> requests){
		roleService.bindMenus(requests);
		return BizResponseUtils.success();
	}

}
