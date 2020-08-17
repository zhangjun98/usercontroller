package com.platform.uc.api.vo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 菜单
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MenuDetailResponse extends MenuResponse{

    private static final long serialVersionUID = 1443168361935089124L;

    /**
     * 父节点名称
     */
    private String parentName;

}
