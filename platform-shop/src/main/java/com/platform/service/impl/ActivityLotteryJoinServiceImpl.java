package com.platform.service.impl;

import com.platform.dao.ActivityLotteryDao;
import com.platform.dao.ActivityLotteryJoinDao;
import com.platform.entity.ActivityLotteryEntity;
import com.platform.entity.ActivityLotteryJoinEntity;
import com.platform.service.ActivityLotteryJoinService;
import com.platform.service.ActivityLotteryService;
import org.quartz.Scheduler;
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
public class ActivityLotteryJoinServiceImpl implements ActivityLotteryJoinService {

    @Autowired
    private ActivityLotteryJoinDao joinDao;

    @Override
    public List<ActivityLotteryJoinEntity> queryList(Map<String, Object> map) {
        return joinDao.queryList(map);
    }

    @Override
    public void updateStatus(Map<String,Object> map) {
         joinDao.updateStatus(map);
    }

    @Override
    public List<Integer> queryIds(Map<String, Object> map) {
        return joinDao.queryIds(map);
    }

    @Override
    public ActivityLotteryJoinEntity queryObject(Object id) {
        return joinDao.queryObject(id);
    }
}
