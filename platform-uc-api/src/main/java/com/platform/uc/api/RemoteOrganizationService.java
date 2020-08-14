package com.platform.uc.api;


import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
	 * 根据条件
	 */
	@PostMapping("/query")
	BizPageResponse<OrganizationResponse> selectByConditions(@RequestBody QueryOrganizationRequest request);

	/**
	 * 更新
	 */
	@PutMapping("/{id}")
	BizResponse<Void> update(@PathVariable String id,@RequestBody OrganizationRequest organizationRequest);

}
