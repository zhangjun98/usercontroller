package com.platform.uc.api.vo.request;

import com.ztkj.framework.response.core.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

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

    private String searchName;

    private Set<String> mids;

}
