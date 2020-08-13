package com.platform.uc.api;

import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.request.Org;
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


	@PostMapping("save") public BizResponse<String> save(@RequestBody Org org);

	@GetMapping("/selectList/{orgName}") public List<Org> selectList(@PathVariable String orgName);

	@GetMapping("/findAllOrg") public BizResponse<List<Org>> findAllOrg();

	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Org org);

	@GetMapping("/search/{id}")  public BizResponse<Org> search(@PathVariable Long id);
}
