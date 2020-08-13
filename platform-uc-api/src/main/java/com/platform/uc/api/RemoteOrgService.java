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
@FeignClient(value = "platform-uc-service", path = "/uc/org",url = "http://192.168.1.205:9000/") public interface RemoteOrgService
{


	@PostMapping("save") public BizResponse<String> save(@RequestBody Organization organization);

	@GetMapping("/selectList") @ResponseBody public BizResponse<List<Organization>> selectList(@RequestParam String orgName);

	@GetMapping("/findAllOrg") public BizResponse<List<Organization>> findAllOrg();

	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Organization organization);

	@GetMapping("/search/{id}")  public BizResponse<Organization> search(@PathVariable Long id);
}
