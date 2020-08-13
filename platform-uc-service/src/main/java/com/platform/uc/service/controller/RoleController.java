package com.platform.uc.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.service.service.RoleService;
import com.platform.uc.service.vo.MemberRole;
import com.platform.uc.service.vo.UcRole;
import com.platform.uc.service.vo.UcRolePermission;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用RestController注解不需要ResponseBody注解
 * RestController注解中包含了ResponseBody注解
 * <p>
 * try{}catch{} 不要用e.printStackTrace(); 打出堆栈信息
 * <p>
 * 错误代码在UserErrorCode中定义
 */
@Slf4j @RestController @RequestMapping("/uc/role") public class RoleController
{

	@Resource private RoleService roleService;

	//角色新增
	@PostMapping("/addRole") public BizResponse<String> addRole(@RequestBody UcRole ucRole)
	{

		try
		{
			roleService.insert(ucRole);
		}
		catch (Exception e)
		{
			return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
		}
		return BizResponseUtils.success("操作成功");
	}

	//角色修改
	@PutMapping("/updateRole") public BizResponse<String> updateRole(@RequestBody UcRole ucRole)
	{
		try
		{
			roleService.update(ucRole);
		}
		catch (Exception e)
		{
			return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
		}
		return BizResponseUtils.success("操作成功");
	}

	//角色查看回显
	@GetMapping("/selectRole/{id}") public BizResponse<UcRole> selectRole(@PathVariable Long id)
	{
		UcRole ucRole = roleService.selectBean(id);
		return BizResponseUtils.success(ucRole);
	}

	//角色列表
	@GetMapping("/selectRoleList/{name}/{pageNum}/{pageSize}") public BizResponse<IPage<UcRole>> selectRoleList(@PathVariable String name, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize)
	{
		IPage<UcRole> ucRoles = roleService.selectList(name, pageNum, pageSize);
		return BizResponseUtils.success(ucRoles);
	}

	//角色删除
	@DeleteMapping("/deleteRole/{id}") public BizResponse<String> deleteRole(@PathVariable Long id)
	{

		UcRole ucRole = new UcRole();
		ucRole.setId(id);
		ucRole.setState(9);
		roleService.update(ucRole);
		return BizResponseUtils.success("操作成功");
	}

	//查看角色下的成员
	@GetMapping("/selectRoleUsers/{roleId}/{pageNum}/{pageSize}") public BizResponse<IPage<MemberRole>> selectRoleUsers(@PathVariable Long roleId, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize)
	{

		IPage<MemberRole> ucMemberRoles = roleService.selectRoleUsers(roleId, pageNum, pageSize);
		return BizResponseUtils.success(ucMemberRoles);
	}

	//权限配置
	@PostMapping("/addRolePermission") public void addRolePermission(@RequestBody UcRolePermission ucRolePermission)
	{
		roleService.insertRolePermission(ucRolePermission);

	}

}
