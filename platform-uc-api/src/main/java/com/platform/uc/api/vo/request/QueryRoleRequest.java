package com.platform.uc.api.vo.request;

import com.ztkj.framework.response.core.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 查询角色
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryRoleRequest extends Page {

    private static final long serialVersionUID = -6103077814235886319L;

    private String name;

    private String roleId;

    private Integer status = 0;

}
