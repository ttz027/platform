package com.platform.service;


import com.platform.entity.ActivityLotteryResultEntity;

import java.util.List;

public interface ActivityLotteryResultService {

    int save(ActivityLotteryResultEntity lotteryResult);

    void saveBatch(List<ActivityLotteryResultEntity> list);
}
