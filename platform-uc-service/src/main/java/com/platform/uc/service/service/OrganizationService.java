package com.platform.uc.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.api.error.UserErrorCode;
import com.platform.uc.api.vo.request.OrganizationRequest;
import com.platform.uc.api.vo.request.QueryOrganizationRequest;
import com.platform.uc.api.vo.response.OrganizationResponse;
import com.platform.uc.api.vo.response.TreeMenuResponse;
import com.platform.uc.api.vo.response.TreeOrgResponse;
import com.platform.uc.service.mapper.OrganizationMapper;
import com.platform.uc.service.vo.Menu;
import com.platform.uc.service.vo.Organization;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BeanUtils;
import com.ztkj.framework.response.utils.BizPageResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	public BizPageResponse<TreeOrgResponse> selectByConditions(QueryOrganizationRequest request) {
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
//		List<OrganizationResponse> responses = organizationPage.getRecords().stream()
//				.map(item->BeanUtils.toT(item, OrganizationResponse.class))
//				.collect(Collectors.toList());

		List<Organization> organizations = organizationMapper.selectList(null);
		List<TreeOrgResponse> treeOrgResponses = JSONObject.parseArray(JSONObject.toJSONString(organizations), TreeOrgResponse.class);
		List<TreeOrgResponse> result = buidTree(organizationPage.getRecords(), treeOrgResponses);
		return BizPageResponseUtils.success((int)page.getSize(), (int)page.getCurrent(), page.getTotal(), result);
	}



	/**
	 * 把一个List转成树
	 */
	private List<TreeOrgResponse> buidTree(List<Organization> list, List<TreeOrgResponse> allList){
		List<TreeOrgResponse> tree = new ArrayList<>();
		for (int i = 0; i < allList.size(); i++) {
			for (int t = 0; t < list.size(); t++) {
				if (list.get(t).getId().equals(allList.get(i).getId())){
					TreeOrgResponse treeOrgResponse = JSONObject.parseObject(JSONObject.toJSONString(list.get(t)), TreeOrgResponse.class);
					//TreeOrgResponse treeOrgResponse = BeanUtils.toT(list.get(t), TreeOrgResponse.class);
					tree.add(findChild(treeOrgResponse,allList));
				}
			}
		}
		return tree;
	}

	private TreeOrgResponse findChild(TreeOrgResponse node, List<TreeOrgResponse> list){
		for(TreeOrgResponse n:list){
			if(n.getParentId().compareTo(node.getId()) == 0){
				if(node.getList() == null){
					node.setList(new ArrayList<>());
				}
				node.getList().add(findChild(n,list));
			}
		}
		return node;
	}
}
