package com.platform.uc.api;

<<<<<<< HEAD
import com.platform.uc.api.vo.request.ClientRequest;
import com.platform.uc.api.vo.request.Org;
import com.platform.uc.api.vo.request.Organization;
import com.platform.uc.api.vo.response.ClientResponse;
=======
import com.platform.uc.api.vo.request.Organization;
>>>>>>> ad3ff147b653f3fe28441703a1498c4392a4c70a
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


<<<<<<< HEAD
	@PostMapping("save") public BizResponse<String> save(@RequestBody Organization org);
=======
	@PostMapping("save") public BizResponse<String> save(@RequestBody Organization organization);
>>>>>>> ad3ff147b653f3fe28441703a1498c4392a4c70a

	@GetMapping("/selectList/{orgName}") public List<Organization> selectList(@PathVariable String orgName);

	@GetMapping("/findAllOrg") public BizResponse<List<Organization>> findAllOrg();

<<<<<<< HEAD
	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Organization org);
=======
	@PostMapping("/update")  public BizResponse<String> update(@RequestBody Organization organization);
>>>>>>> ad3ff147b653f3fe28441703a1498c4392a4c70a

	@GetMapping("/search/{id}")  public BizResponse<Organization> search(@PathVariable Long id);
}
