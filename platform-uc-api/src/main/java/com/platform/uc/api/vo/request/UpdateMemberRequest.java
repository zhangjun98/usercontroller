package com.platform.uc.api.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * 修改用户信息
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateMemberRequest extends RegisterMemberRequest {

    private static final long serialVersionUID = -6421397993756146012L;

    /**
     * 用户信息编号
     */
    private String id;

}
