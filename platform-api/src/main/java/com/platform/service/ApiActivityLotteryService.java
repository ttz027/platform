package com.platform.service;

import com.platform.dao.ApiActivityLotteryMapper;
import com.platform.entity.ActivityLotteryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/23 0023 上午 11:33
 */
@Service
public class ApiActivityLotteryService {
    @Autowired
    private ApiActivityLotteryMapper mapper;

    public ActivityLotteryVo queryObject(Integer id) {
        return mapper.queryObject(id);
    }

    public List<ActivityLotteryVo> queryList(Map<String, Object> map) {
        return mapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return mapper.queryTotal(map);
    }

    public int save(ActivityLotteryVo ad) {
        ad.setCreateTime(new Date());
        return mapper.save(ad);
    }

    public int update(ActivityLotteryVo ad) {
        return mapper.update(ad);
    }

    public int delete(Integer id) {
        return mapper.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return mapper.deleteBatch(ids);
    }
}
