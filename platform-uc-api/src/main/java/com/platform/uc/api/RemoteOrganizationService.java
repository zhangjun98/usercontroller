package com.platform.uc.api;


import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
<<<<<<< .mine
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.api.vo.response.TreeOrganizationResponse;
=======
import com.platform.uc.api.vo.response.TreeOrganizationResponse;

>>>>>>> .theirs
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 远程用户信息接口
 *
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/organization")
public interface RemoteOrganizationService {

	/**
	 * 保存
	 */
	@PostMapping("/save")
	BizResponse<Void> save(@RequestBody OrganizationRequest organizationRequest);

	/**
	 * 更新
	 */
	@PutMapping("/{id}")
	BizResponse<Void> modify(@PathVariable String id,@RequestBody OrganizationRequest organizationRequest);

	/**
	 * 根据条件
	 */
	@PostMapping("/query")
	BizPageResponse<TreeOrganizationResponse> selectByConditions(@RequestBody QueryOrganizationRequest request);

	/**
<<<<<<< .mine
	 * 详情
	 * @param id
	 * @return




=======
	 * 树形菜单
	 */
	@PostMapping("/tree")
	BizPageResponse<TreeOrganizationResponse> tree(@RequestBody Set<String> pids);

	/**
	 * 更新
>>>>>>> .theirs
	 */
	@GetMapping("/{id}")
	BizResponse<OrganizationResponse> detail(@PathVariable String id);

	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@PostMapping("/remove")
	BizResponse<Void> remove(@RequestBody BatchRequest request);

}
