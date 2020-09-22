package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/22 0022 上午 10:19
 */
@Data
public class GoodsPlaycallNumEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer goodsId;
    private Integer initCallNum;
    private Integer callNum;
    private Integer collectNum;

    public GoodsPlaycallNumEntity() {
    }
    public GoodsPlaycallNumEntity(Integer goodsId) {
        this.goodsId = goodsId;
    }
}
