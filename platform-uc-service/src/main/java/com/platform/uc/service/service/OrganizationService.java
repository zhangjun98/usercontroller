package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.OrganizationMapper;
import com.platform.uc.service.vo.Organization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机构管理的service
 */
@Service public class OrganizationService
{

	@Resource private OrganizationMapper organizationMapper;

	/**
	 * 保存机构信息
	 *
	 * @param organization
	 * @return
	 */
	public void save(Organization organization)
	{
		organizationMapper.insert(organization);
	}

	/**
	 * 查询机构的树状结构
	 *
	 * @param orgName
	 * @return
	 */
	public List<Organization> selectList(String orgName)
	{
		//查询所有的机构
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(orgName))
		{
			queryWrapper.like("org_name", orgName).or().like("short_name", orgName).or().like("org_code", orgName);
		}
		queryWrapper.eq("state", 0);
		return  organizationMapper.selectList(queryWrapper);
	}

	/**
	 * 查询所有机构信息,平铺前端保存机构时,要选择所属机构
	 *
	 * @return
	 */
	public List<Organization> findAllOrg()
	{
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("state", 0);
		List<Organization> organizations = organizationMapper.selectList(queryWrapper);
		return organizations;
	}

	/**
	 * 更新机构信息
	 *
	 * @param organization
	 * @return
	 */
	public void update(Organization organization)
	{
		organizationMapper.updateById(organization);
	}

	/**
	 * 根据id查询单个机构信息
	 *
	 * @param id 机构的id
	 * @return org 机构对象
	 */
	public Organization search(Long id)
	{

		return organizationMapper.selectById(id);

	}

}
