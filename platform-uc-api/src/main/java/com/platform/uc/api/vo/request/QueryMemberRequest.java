package com.platform.uc.api.vo.request;

import com.ztkj.framework.response.core.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 查询角色
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryMemberRequest extends Page {

    private static final long serialVersionUID = 4506241033559575226L;

    private String id;

    private String searchName;

}
