package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_activity_lottery_join
 *
 */
@Data
public class ActivityLotteryJoinEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private Integer lotteryId;
    private Integer status;
    private Date createTime;

}
