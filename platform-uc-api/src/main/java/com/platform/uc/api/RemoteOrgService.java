package com.platform.uc.api;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.request.Org;
import com.platform.uc.api.vo.request.Organization;
import com.platform.uc.api.vo.response.ClientResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户信息接口
 *
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/org") public interface RemoteOrgService
{


	@PostMapping("save") public BizResponse<String> save(@RequestBody Organization org);

	@GetMapping("/selectList/{orgName}") public List<Organization> selectList(@PathVariable String orgName);

	@GetMapping("/findAllOrg") public BizResponse<List<Organization>> findAllOrg();

	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Organization org);

	@GetMapping("/search/{id}")  public BizResponse<Organization> search(@PathVariable Long id);
}
