package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 开发者
 * @author hao.yan
 */
@Data
public class DeveloperRequest implements Serializable {

    private static final long serialVersionUID = 1347566098455725751L;

    /**
     * 开发者编号
     */
    private String id;

    /**
     * 开发者名称
     */
    private String name;


}
