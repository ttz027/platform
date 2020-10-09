package com.platform.service.impl;

import com.platform.dao.ActivityLotteryJoinDao;
import com.platform.dao.ActivityLotteryResultDao;
import com.platform.entity.ActivityLotteryJoinEntity;
import com.platform.entity.ActivityLotteryResultEntity;
import com.platform.service.ActivityLotteryJoinService;
import com.platform.service.ActivityLotteryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/23 0023 上午 11:33
 */
@Service
public class ActivityLotteryResultServiceImpl implements ActivityLotteryResultService {

    @Autowired
    private ActivityLotteryResultDao resultDao;

    @Override
    public int save(ActivityLotteryResultEntity lotteryResult) {
        return resultDao.save(lotteryResult);
    }

    @Override
    public void saveBatch(List<ActivityLotteryResultEntity> list) {
        resultDao.saveBatch(list);
    }
}
