package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 批量操作
 * @author hao.yan
 */
@Data
public class BatchRequest implements Serializable {

    private static final long serialVersionUID = -1919234456737533062L;

    private Set<String> ids;

}
