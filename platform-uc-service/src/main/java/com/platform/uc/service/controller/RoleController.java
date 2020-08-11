package com.platform.uc.service.controller;

import com.platform.uc.service.service.RoleService;
import com.platform.uc.service.vo.Menu;
import com.platform.uc.service.vo.UcRole;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/uc/role") @RestController public class RoleController
{
	@Autowired RoleService roleService;

	//角色新增
	@PostMapping("/addRole") @ResponseBody public BizResponse<String> addRole(@RequestBody UcRole ucRole)
	{
		if (StringUtils.isEmpty(ucRole.getName()))
		{
			return BizResponseUtils.error("999999", "角色名不能为空");

		}
		try
		{
			roleService.insert(ucRole);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
		}
		return BizResponseUtils.success("操作成功");
	}

	//角色修改
	@PutMapping("/updateRole") @ResponseBody public BizResponse<String> updateRole(@RequestBody UcRole ucRole)
	{
		if (StringUtils.isEmpty(ucRole.getName()))
		{
			return BizResponseUtils.error("999999", "角色名不能为空");
		}
		if (ucRole.getId() == null)
		{
			return BizResponseUtils.error("999999", "主键不能为空");
		}
		try
		{
			roleService.update(ucRole);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
		}
		return BizResponseUtils.success("操作成功");
	}

	//角色查看回显
	@GetMapping("/selectRole") @ResponseBody public BizResponse<UcRole> selectRole(@PathVariable Long id)
	{
		if (id == null)
		{
			return BizResponseUtils.error("999999", "主键不能为空");
		}
		UcRole ucRole = roleService.selectBean(id);
		return BizResponseUtils.success(ucRole);
	}


	//角色列表
	@GetMapping("/selectRoleList") @ResponseBody public BizResponse<List<UcRole>> selectRoleList(@PathVariable String name)
	{

		List<UcRole> ucRoles = roleService.selectList(name);
		return BizResponseUtils.success(ucRoles);
	}

	//角色删除
	@DeleteMapping("/deleteRole") @ResponseBody public BizResponse<String> deleteRole(@PathVariable Long id)
	{
		if (id == null)
		{
			return BizResponseUtils.error("999999", "主键不能为空");
		}
		UcRole ucRole = new UcRole();
		ucRole.setId(id);
		ucRole.setState(9);
		roleService.update(ucRole);
		return BizResponseUtils.success("操作成功");
	}
}
