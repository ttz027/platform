package com.platform.service;

import com.platform.entity.ActivityLotteryEntity;
import com.platform.entity.ActivityLotteryJoinEntity;

import java.util.List;
import java.util.Map;

public interface ActivityLotteryJoinService {

    List<ActivityLotteryJoinEntity> queryList(Map<String, Object> map);

    void updateStatus(Map<String,Object> map);

    List<Integer> queryIds(Map<String, Object> map);

    ActivityLotteryJoinEntity queryObject(Object id);
}
