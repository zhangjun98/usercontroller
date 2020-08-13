package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 修改用户状态 enabled
 * @author hao.yan
 */
@Data
public class ChangeStatusRequest implements Serializable {

    private static final long serialVersionUID = -9113231501307290853L;

    private boolean enable = Boolean.TRUE;

    /**
     * 用户编号集合
     */
    private Set<String> userIds;

}
