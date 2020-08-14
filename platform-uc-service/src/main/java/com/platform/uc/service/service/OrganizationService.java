package com.platform.uc.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.api.vo.response.RoleResponse;
import com.platform.uc.api.vo.response.TreeOrganizationResponse;
import com.platform.uc.service.mapper.OrganizationMapper;
import com.platform.uc.service.vo.Organization;
import com.platform.uc.service.vo.Role;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		organization.setCreatorId(request.getOperator());
		organization.setCreateDate(new Date());
		int count = organizationMapper.insert(organization);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_INSERT_FAIL);
		}
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
	 * 机构详情
	 * @param id
	 * @return
	 */
	public OrganizationResponse detail(String id) {
		Organization org = organizationMapper.selectById(id);
		return BeanUtils.toT(org, OrganizationResponse.class);
	}

	/**
	 * 更新状态
	 * 0: 正常
	 * 1: 删除状态
	 */
	public void changeStatus(BatchRequest request, Integer status){
		UpdateWrapper<Organization> wrapper = new UpdateWrapper<>();
		wrapper.in("id", request.getIds());
		Organization org = new Organization();
		org.setStatus(status);
		org.setUpdaterId(request.getOperator());
		org.setUpdateDate(new Date());
		int count = organizationMapper.update(org, wrapper);
		if (count <= 0){
			throw new BizException(UserErrorCode.ROLE_DELETE_FAIL);
		}
	}

	/**
	 * 查询机构
	 */
	public BizPageResponse<TreeOrganizationResponse> selectByConditions(QueryOrganizationRequest request) {
		Page<Organization> page = new Page<>();
		page.setSize(request.getPageSize());
		page.setCurrent(request.getPageNo());
		//查询所有的机构
		QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(request.getSearchName())) {
			queryWrapper
						.like("name", request.getSearchName())
					.or()
						.like("short_name", request.getSearchName())
					.or()
					 	.like("code", request.getSearchName());
		}

		if (StringUtils.isNotEmpty(request.getId())){
			queryWrapper.eq("id", request.getId());
		}

		queryWrapper.eq("status", request.getStatus());
		Page<Organization> organizationPage = organizationMapper.selectPage(page, queryWrapper);
		if(CollectionUtils.isEmpty(organizationPage.getRecords())){
			return BizPageResponseUtils.success(new ArrayList<>());
		}

		List<Organization> organizations = organizationMapper.selectList(null);
		List<TreeOrganizationResponse> treeOrgResponses = JSONObject.parseArray(JSONObject.toJSONString(organizations), TreeOrganizationResponse.class);
		List<TreeOrganizationResponse> result = buidTree(organizationPage.getRecords(), treeOrgResponses);
		return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), result);
	}



	/**
	 * 把一个List转成树
	 */
	private List<TreeOrganizationResponse> buidTree(List<Organization> list, List<TreeOrganizationResponse> allList){
		List<TreeOrganizationResponse> tree = new ArrayList<>();
		for (int i = 0; i < allList.size(); i++) {
			for (int t = 0; t < list.size(); t++) {
				if (list.get(t).getId().equals(allList.get(i).getId())){
					TreeOrganizationResponse treeOrgResponse = JSONObject.parseObject(JSONObject.toJSONString(list.get(t)), TreeOrganizationResponse.class);
					//TreeOrganizationResponse treeOrgResponse = BeanUtils.toT(list.get(t), TreeOrganizationResponse.class);
					tree.add(findChild(treeOrgResponse,allList));
				}
			}
		}
		return tree;
	}

	private TreeOrganizationResponse findChild(TreeOrganizationResponse node, List<TreeOrganizationResponse> list){
		for(TreeOrganizationResponse n:list){
			if(n.getParentId().compareTo(node.getId()) == 0){
				if(node.getChildren() == null){
					node.setChildren(new ArrayList<>());
				}
				node.getChildren().add(findChild(n,list));
			}
		}
		return node;
	}
}
