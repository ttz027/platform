package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/22 0022 上午 10:19
 */
@Data
public class GoodsPlaycallMappingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer godosId;
    private Integer type;

}
