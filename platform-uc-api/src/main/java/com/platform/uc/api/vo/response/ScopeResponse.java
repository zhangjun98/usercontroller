package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求的范围
 * @author hao.yan
 */
@Data
public class ScopeResponse implements Serializable {

    private static final long serialVersionUID = -3578155282903267933L;

    private String id;

    /**
     * 范围名称
     * 全局唯一
     */
    private String name;

    /**
     * 请求范围描述
     */
    private String describe;

    private Date createTime;

    private Date updateTime;

}
