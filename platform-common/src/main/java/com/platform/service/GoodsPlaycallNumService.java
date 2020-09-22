package com.platform.service;

import com.platform.entity.GoodsPlaycallNumEntity;

public interface GoodsPlaycallNumService {

    int initPlaycall(GoodsPlaycallNumEntity goods);

    int playCall(Integer userId,Integer goodsId,Integer type,Integer num);

    int addPlaycall(GoodsPlaycallNumEntity goods);

    GoodsPlaycallNumEntity  getById(Integer goodsId);
}
