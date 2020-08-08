package com.platform.uc.adapter.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 申请范围
 * @author hao.yan
 */
@Data
public class Scope implements Serializable {

    private static final long serialVersionUID = 1029435973399136582L;

    private String name;

    private boolean checked;

    private String describe;

}
