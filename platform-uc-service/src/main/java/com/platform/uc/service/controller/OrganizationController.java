package com.platform.uc.service.controller;

import com.platform.uc.service.service.OrganizationService;
import com.platform.uc.service.vo.Organization;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理
 */
@RequestMapping("/org") @Controller public class OrganizationController
{

	@Autowired private OrganizationService organizationService;

	/**
	 * 保存机构
	 *
	 * @return
	 */
	@PostMapping("/save") @ResponseBody public BizResponse<String> save(@RequestBody Organization organization)
	{
		try
		{
			organizationService.save(organization);
			return BizResponseUtils.success("操作成功");
		}
		catch (Exception e)
		{
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 根据条件,查询机构的目录树
	 *
	 * @return
	 */
	@GetMapping("/selectList") @ResponseBody public BizResponse<List<Organization>> selectList(String orgName)
	{
		try
		{
			List<Organization> tree = organizationService.selectList(orgName);
			return BizResponseUtils.success(tree);
		}
		catch (Exception e)
		{
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 查询所有的机构,平铺
	 *
	 * @return
	 */
	@GetMapping("/findAllOrg") @ResponseBody public BizResponse<List<Organization>> findAllOrg()
	{
		try
		{
			List<Organization> tree = organizationService.findAllOrg();
			return BizResponseUtils.success(tree);
		}
		catch (Exception e)
		{
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 编辑机构
	 *
	 * @return 修改成功返回1
	 */
	@PostMapping("/update") @ResponseBody public BizResponse<String> update(@RequestBody Organization organization)
	{
		try
		{
			organizationService.update(organization);
			return BizResponseUtils.success("操作成功");
		}
		catch (Exception e)
		{
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 根据id查询单个机构信息
	 *
	 * @return
	 */
	@GetMapping("/search/{id}") @ResponseBody public BizResponse<Organization> search(@PathVariable Long id)
	{
		try
		{
			Organization organization = organizationService.search(id);
			return BizResponseUtils.success(organization);
		}
		catch (Exception e)
		{
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

}
