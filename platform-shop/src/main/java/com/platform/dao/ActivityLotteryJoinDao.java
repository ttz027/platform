package com.platform.dao;

import com.platform.entity.ActivityLotteryJoinEntity;

import java.util.List;
import java.util.Map;

public interface ActivityLotteryJoinDao extends BaseDao<ActivityLotteryJoinEntity> {
    List<Integer> queryIds(Map<String, Object> map);

    void updateStatus(Map<String,Object> map);
}
