package adapter.service;

import com.platform.uc.api.RemoteOrgService;
import com.platform.uc.api.vo.request.Org;
import com.ztkj.framework.response.core.BizResponse;
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

	@Resource private RemoteOrgService remoteOrgService;

	/**
	 * 保存机构信息
	 *
	 * @param org
	 * @return
	 */
	public BizResponse<String> save(Org org)
	{
		if (org.getParentId() == null)
		{
			org.setParentId(0l);
		}
		org.setState(0l);
		org.setCreateDate(new Date());
		return remoteOrgService.save(org);
	}

}
