package com.platform.uc.api;

import com.platform.uc.api.vo.request.*;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
	BizResponse<RoleRequest> detail(@PathVariable String id);

	@PostMapping("/remove")
	BizResponse<Void> remove(@RequestBody BatchRequest request);

	@PostMapping("/addRolePermission")
	BizResponse<Void> addRolePermission(@RequestBody UcRolePermission ucRolePermission);
}
