package com.platform.uc.api;


import com.platform.uc.api.vo.request.Organization;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户信息接口
 *
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/org") public interface RemoteOrgService
{



	@PostMapping("save") public BizResponse<String> save(@RequestBody Organization organization);

	@GetMapping("/selectList/{orgName}") public List<Organization> selectList(@PathVariable String orgName);

	@GetMapping("/findAllOrg") public BizResponse<List<Organization>> findAllOrg();


	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Organization organization);

	@GetMapping("/search/{id}")  public BizResponse<Organization> search(@PathVariable Long id);
}
