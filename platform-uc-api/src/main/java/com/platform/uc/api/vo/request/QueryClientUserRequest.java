package com.platform.uc.api.vo.request;

import com.ztkj.framework.response.core.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 查询用户
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryClientUserRequest extends Page {

    private static final long serialVersionUID = -6103077814235886319L;

    private String clientId;

}
