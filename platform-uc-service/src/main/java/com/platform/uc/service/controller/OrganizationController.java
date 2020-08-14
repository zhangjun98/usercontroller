package com.platform.uc.service.controller;

import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.service.service.OrganizationService;
import com.platform.uc.service.vo.Organization;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
	 * 根据条件
	 */
	@PostMapping("/query")
	public BizPageResponse<OrganizationResponse> selectByConditions(@RequestBody QueryOrganizationRequest request){
		return organizationService.selectByConditions(request);
	}

}
