package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.OrgMapper;
import com.platform.uc.service.vo.Org;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 机构管理的service
 */
@Service public class OrgService
{

	@Resource private OrgMapper orgMapper;

	/**
	 * 保存机构信息
	 *
	 * @param org
	 * @return
	 */
	public void save(Org org)
	{
		orgMapper.insert(org);
	}

	/**
	 * 查询机构的树状结构
	 *
	 * @param orgName
	 * @return
	 */
	public List<Org> selectList(String orgName)
	{
		//查询所有的机构
		QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(orgName))
		{
			queryWrapper.like("org_name", orgName).or().like("short_name", orgName).or().like("org_code", orgName);
		}
		queryWrapper.eq("state", 0);
		return  orgMapper.selectList(queryWrapper);
	}

	/**
	 * 查询所有机构信息,平铺前端保存机构时,要选择所属机构
	 *
	 * @return
	 */
	public List<Org> findAllOrg()
	{
		QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("state", 0);
		List<Org> orgs = orgMapper.selectList(queryWrapper);
		return orgs;
	}

	/**
	 * 更新机构信息
	 *
	 * @param org
	 * @return
	 */
	public void update(Org org)
	{
		orgMapper.updateById(org);
	}

	/**
	 * 根据id查询单个机构信息
	 *
	 * @param id 机构的id
	 * @return org 机构对象
	 */
	public Org search(Long id)
	{

		return orgMapper.selectById(id);

	}

}
