package com.platform.uc.api;

import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.RoleMenuResponse;
import com.platform.uc.api.vo.response.RoleResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * 远程用户信息接口
 *
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/role")
public interface RemoteRoleService {

	@PostMapping("/query")
	BizPageResponse<RoleResponse> selectByConditions(@RequestBody QueryRoleRequest request);

	@PostMapping("/")
	BizResponse<Void> save(@RequestBody RoleRequest roleRequest);

	@PutMapping("/{id}")
	BizResponse<Void> modify(@PathVariable String id, @RequestBody RoleRequest roleRequest);

	@GetMapping("/{id}")
	BizResponse<RoleResponse> detail(@PathVariable String id);

	@PostMapping("/remove")
	BizResponse<Void> remove(@RequestBody BatchRequest request);

	/**
	 * 权限配置
	 */
	@PostMapping("/bind/menus")
	BizResponse<Void> bindMenus(@RequestBody Collection<BindMenuRequest> requests);

	/**
	 * 查询权限所有根菜单
	 */
	@GetMapping("/menus/{id}")
	BizResponse<List<RoleMenuResponse>> findModelMenusByRoleId(@PathVariable String id);

	/**
	 * 查询用户下的角色
	 */
	@GetMapping("/user/{mid}")
	BizPageResponse<RoleResponse> selectRoleByMid(@PathVariable String mid);

}
