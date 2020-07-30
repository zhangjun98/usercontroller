package com.platform.uc.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开发者
 * @author hao.yan
 */
@Data
public class DeveloperResponse implements Serializable {

    private static final long serialVersionUID = -166132554286769908L;

    /**
     * 开发者编号
     */
    private String id;

    /**
     * 开发者名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
