package com.platform.uc.service.controller;

import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.api.vo.response.TreeOrganizationResponse;
import com.platform.uc.service.service.OrganizationService;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 机构管理
 */
@RequestMapping("/organization")
@RestController
public class OrganizationController {

	@Resource
	private OrganizationService organizationService;

	/**
	 * 保存机构
	 */
	@PostMapping("/save")
	public BizResponse<Void> save(@RequestBody OrganizationRequest request){
		organizationService.save(request);
		return BizResponseUtils.success();
	}

	/**
	 * 编辑机构
	 */
	@PutMapping("/{id}")
	public BizResponse<Void> modify(@PathVariable String id, @RequestBody OrganizationRequest request){
		organizationService.update(id, request);
		return BizResponseUtils.success();
	}

	/**
	 * 机构详情
	 */
	@GetMapping("/{id}")
	public BizResponse<OrganizationResponse> detail(@PathVariable String id) {
		OrganizationResponse org = organizationService.detail(id);
		return BizResponseUtils.success(org);
	}

	/**
	 * 移除
	 */
	@PostMapping("/remove")
	public BizResponse<Void> remove(@RequestBody BatchRequest request){
		UserErrorCode code = organizationService.changeStatus(request, 9);
		if (code.getCode().equals(UserErrorCode.SUCCESS.getCode()))  {
			return BizResponseUtils.success();
		} else if (code.getCode().equals(UserErrorCode.DELETE_NOD_FAIL_HAS_CHILD.getCode())){
			return BizResponseUtils.error(UserErrorCode.DELETE_NOD_FAIL_HAS_CHILD);
		} else {
			return BizResponseUtils.error(UserErrorCode.DELETE_ERROR);
		}

	}

	/**
	 * 根据条件
	 */
	@PostMapping("/query")
	public BizPageResponse<OrganizationResponse> selectByConditions(@RequestBody QueryOrganizationRequest request){
		return organizationService.selectByConditions(request);
	}


	/**
	 * 树形菜单
	 */
	@PostMapping("/tree")
	public BizPageResponse<TreeOrganizationResponse> tree(@RequestBody Set<String> pids){
		return organizationService.tree(pids);
	}

}
