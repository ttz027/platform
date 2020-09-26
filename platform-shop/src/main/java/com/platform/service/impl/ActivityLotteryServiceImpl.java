package com.platform.service.impl;

import com.platform.dao.ActivityLotteryDao;
import com.platform.entity.ActivityLotteryEntity;
import com.platform.service.ActivityLotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.quartz.Scheduler;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/23 0023 上午 11:33
 */
@Service
public class ActivityLotteryServiceImpl implements ActivityLotteryService {
    @Autowired
    private ActivityLotteryDao dao;
    @Autowired
    private Scheduler scheduler;

    @Override
    public ActivityLotteryEntity queryObject(Integer id) {
        return dao.queryObject(id);
    }

    @Override
    public List<ActivityLotteryEntity> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return dao.queryTotal(map);
    }

    @Override
    public int save(ActivityLotteryEntity ad) {
        ad.setCreateTime(new Date());
        int result = dao.save(ad);
        return result;
    }

    @Override
    public int update(ActivityLotteryEntity ad) {
        return dao.update(ad);
    }

    @Override
    public int delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return dao.deleteBatch(ids);
    }
}
