package com.platform.service.impl;

import com.platform.dao.GoodsPlaycallMappingDao;
import com.platform.dao.GoodsPlaycallNumDao;
import com.platform.entity.GoodsPlaycallMappingEntity;
import com.platform.entity.GoodsPlaycallNumEntity;
import com.platform.service.GoodsPlaycallNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/22 0022 上午 10:45
 */
@Service("goodsPlaycallNumService")
public class GoodsPlaycallNumServiceImpl implements GoodsPlaycallNumService {

    @Autowired
    private GoodsPlaycallNumDao goodsPlaycallNumDao;
    @Autowired
    private GoodsPlaycallMappingDao goodsPlaycallMappingDao;


    public int initPlaycall(GoodsPlaycallNumEntity goods){
       return goodsPlaycallNumDao.update(goods);
    }


    public int playCall(Integer userId,Integer goodsId,Integer type,Integer num){
        GoodsPlaycallNumEntity numEntity = new GoodsPlaycallNumEntity();
        numEntity.setGoodsId(goodsId);
        if(type==1){
            numEntity.setCallNum(num);
        }else{
            numEntity.setCollectNum(num);
        }
        GoodsPlaycallMappingEntity mappingEntity = new GoodsPlaycallMappingEntity();
        mappingEntity.setUserId(userId);
        mappingEntity.setGoodsId(goodsId);
        mappingEntity.setType(type);
        try {
            goodsPlaycallNumDao.update(numEntity);
            goodsPlaycallMappingDao.save(mappingEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int addPlaycall(GoodsPlaycallNumEntity goods) {
        return goodsPlaycallNumDao.save(goods);
    }

    @Override
    public GoodsPlaycallNumEntity getById(Integer goodsId) {
        return goodsPlaycallNumDao.queryObject(goodsId);
    }
}
