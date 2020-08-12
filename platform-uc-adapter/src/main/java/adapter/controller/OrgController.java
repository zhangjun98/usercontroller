package adapter.controller;

import adapter.service.OrgService;
import com.platform.uc.api.vo.request.Org;
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
		if(org == null)
		{
			return BizResponseUtils.error("999999", "不能为空");
		}
		try
		{
			return orgService.save(org);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return BizResponseUtils.error("999999", "系统繁忙请稍后重试");
	}

}
