package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.api.vo.response.TreeOrganizationResponse;
import com.platform.uc.service.mapper.OrganizationMapper;
import com.platform.uc.service.vo.Organization;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 机构管理的service
 */
@Service
public class OrganizationService {

	@Resource
	private OrganizationMapper organizationMapper;

	/**
	 * 保存机构信息
	 */
	public void save(OrganizationRequest request) {
		Organization organization = BeanUtils.toT(request, Organization.class);
		organizationMapper.insert(organization);
	}


	/**
	 * 更新机构信息
	 */
	public void update(String id, OrganizationRequest request) {
		Organization organization = organizationMapper.selectById(id);
		if (organization == null){
			throw new BizException(UserErrorCode.ROLE_NOTFOUND);
		}
		Organization newOrganization = BeanUtils.toT(request, Organization.class);
		newOrganization.setId(organization.getId());
		newOrganization.setUpdateDate(new Date());
		newOrganization.setUpdaterId(request.getOperator());
		int count = organizationMapper.updateById(newOrganization);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_UPDATE_FAIL);
		}

	}

	/**
	 * 查询机构
	 */
	public BizPageResponse<OrganizationResponse> selectByConditions(QueryOrganizationRequest request) {
		Page<Organization> page = new Page<>();
		page.setSize(request.getPageSize());
		page.setCurrent(request.getPageNo());
		//查询所有的机构
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(request.getSearchName())) {
			queryWrapper
						.like("org_name", request.getSearchName())
					.or()
						.like("short_name", request.getSearchName())
					.or()
					 	.like("org_code", request.getSearchName());
		}

		if (StringUtils.isNotEmpty(request.getId())){
			queryWrapper.eq("id", request.getId());
		}

		queryWrapper.eq("status", request.getStatus());
		Page<Organization> organizationPage = organizationMapper.selectPage(page, queryWrapper);
		if(CollectionUtils.isEmpty(organizationPage.getRecords())){
			return BizPageResponseUtils.success(new ArrayList<>());
		}
		List<OrganizationResponse> responses = organizationPage.getRecords().stream()
				.map(item->BeanUtils.toT(item, OrganizationResponse.class))
				.collect(Collectors.toList());
		return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), responses);
	}

	public BizPageResponse<TreeOrganizationResponse> tree(Set<String> pids){
		List<Organization> organizations = recursion(pids);
		if (CollectionUtils.isEmpty(organizations)){
			return BizPageResponseUtils.success(new ArrayList<>());
		}
		List<TreeOrganizationResponse> tree = buidTree(organizations.stream()
				.map(item->BeanUtils.toT(item, TreeOrganizationResponse.class))
				.collect(Collectors.toList()));

		return BizPageResponseUtils.success(tree);
	}

	/**
	 * 把一个List转成树
	 */
	private List<TreeOrganizationResponse> buidTree(List<TreeOrganizationResponse> list){
		List<TreeOrganizationResponse> tree = new ArrayList<>();
		for(TreeOrganizationResponse node:list){
			if(node.getParentId().equals("0")){
				tree.add(findChild(node,list));
			}
		}
		return tree;
	}

	private TreeOrganizationResponse findChild(TreeOrganizationResponse node, List<TreeOrganizationResponse> list){
		for(TreeOrganizationResponse n:list){
			if(n.getParentId().equals(node.getId())){
				if(node.getChildren() == null){
					node.setChildren(new ArrayList<>());
				}
				node.getChildren().add(findChild(n,list));
			}
		}
		return node;
	}

	/**
	 * 递归查询某个节点下的所有子节点
	 */
	private List<Organization> recursion(Set<String> pids){
		List<Organization> organizations = selectByPids(pids);
		if (CollectionUtils.isEmpty(organizations)){
			return organizations;
		}
		organizations.addAll(recursion(organizations.stream()
				.map(Organization::getId)
				.collect(Collectors.toSet())));
		return organizations;
	}


	private List<Organization> selectByPids(Set<String> pids){
		QueryWrapper<Organization> wrapper = new QueryWrapper<>();
		wrapper.in("parent_id", pids);
		List<Organization> organizations = organizationMapper.selectList(wrapper);
		return organizationMapper.selectList(wrapper);
	}

}
