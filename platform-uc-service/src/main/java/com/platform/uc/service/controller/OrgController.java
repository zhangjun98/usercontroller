package com.platform.uc.service.controller;

import com.platform.uc.service.service.OrgService;
import com.platform.uc.service.vo.Org;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 机构管理
 */
@RequestMapping("/org") @Controller public class OrgController
{

	@Autowired private OrgService orgService;


	/**
	 * 保存机构
	 *
	 * @return
	 */
	@PostMapping("/save") @ResponseBody public BizResponse<String> save(@RequestBody Org org)
	{
		try
		{
			orgService.save(org);
			return BizResponseUtils.success("操作成功");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 根据条件,查询机构的目录树
	 *
	 * @return
	 */
	@GetMapping("/selectList") @ResponseBody public BizResponse<List<Org>> selectList(String orgName)
	{
		try
		{
			List<Org> tree = orgService.selectList(orgName);
			return BizResponseUtils.success(tree);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 查询所有的机构,平铺
	 *
	 * @return
	 */
	@GetMapping("/findAllOrg") @ResponseBody public BizResponse<List<Org>> findAllOrg()
	{
		try
		{
			List<Org> tree = orgService.findAllOrg();
			return BizResponseUtils.success(tree);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 编辑机构
	 *
	 * @return 修改成功返回1
	 */
	@PostMapping("/update") @ResponseBody public BizResponse<String> update(@RequestBody Org org)
	{
		try
		{
			orgService.update(org);
			return BizResponseUtils.success("操作成功");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

	/**
	 * 根据id查询单个机构信息
	 *
	 * @return
	 */
	@GetMapping("/search/{id}") @ResponseBody public BizResponse<Org> search(@PathVariable Long id)
	{
		try
		{
			Org org = orgService.search(id);
			return BizResponseUtils.success(org);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}


}
