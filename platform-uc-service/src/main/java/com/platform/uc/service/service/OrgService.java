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
	//    public Integer save(Org org) {
	//
	//        if (org!=null) {
	//            if (org.getParentId()==null){
	//                org.setParentId(0l);
	//            }
	//            org.setState(0l);
	//            org.setCreateDate(new Date());
	//            int insert = orgMapper.insert(org);
	//            return insert;
	//        }
	//        return null;
	//    }
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
	public List<Org> findTree(String orgName)
	{
		//查询所有的机构
		QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(orgName))
		{
			queryWrapper.like("org_name", orgName).or().like("short_name", orgName).or().like("org_code", orgName);
		}
		queryWrapper.eq("state", 0);
		List<Org> orgs = orgMapper.selectList(queryWrapper);
		if (orgs != null && orgs.size() > 0)
		{

			//所有滴结果放在map中
			HashMap<Long, Object> map = new HashMap<>(orgs.size());
			for (Org org : orgs)
			{
				map.put(org.getId(), org);
			}

			//继续遍历总list,每个元素的父级找出来,添加
			for (int i = 0; i < orgs.size(); i++)
			{
				Org o = (Org) map.get(orgs.get(i).getParentId());
				if (o != null)
				{
					//添加子集前,给一个list地址
					if (o.getList() == null)
					{
						o.setList(new ArrayList<Org>());
					}

					o.getList().add(orgs.get(i));
					map.put(orgs.get(i).getParentId(), o);
				}
			}

			//拿到一级机构,返回
			ArrayList<Org> orgsResult = new ArrayList<>();
			for (int i = 0; i < orgs.size(); i++)
			{
				if (orgs.get(i).getParentId() == 0)
				{
					orgsResult.add(orgs.get(i));
				}
			}

			return orgsResult;
		}
		return null;
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
	public Integer update(Org org)
	{
		if (org == null || org.getId() == null)
		{
			throw new RuntimeException("更新机构信息时参数信息错误!");
		}
		org.setUpdateDate(new Date());
		int count = orgMapper.updateById(org);
		return count;
	}

	/**
	 * 根据id查询单个机构信息
	 *
	 * @param id 机构的id
	 * @return org 机构对象
	 */
	public Org search(Long id)
	{
		if (id != null)
		{

			Org org = orgMapper.selectById(id);
			if (org.getState() == 1l)
			{
				org = null;
			}

			return org;
		}

		return null;
	}

	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @return
	 */
	public Integer delete(Long id)
	{
		Integer i = null;
		if (id != null)
		{
			Org org = orgMapper.selectById(id);
			if (org != null)
			{
				org.setState(9l);
				i = orgMapper.updateById(org);
			}
		}
		return i;
	}
}
