package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_activity_lottery
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-10-02 14:11:24
 */
@Data
public class ActivityLotteryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer placesNum;
    private Integer prizeType;
    private Integer prizeId;
    private String prizeName;
    private Date onlineTime;
    private Date downlineTime;
    private Date openTime;
    private Date createTime;
    private Integer lotteryStatus;
    private Integer status;


    public static final String START_JOB_NAME = "startLotteryJob_";
    public static final String END_JOB_NAME = "endLotteryJob_";
    public static final String START_JOB_GROUP = "startLotteryGroup_";
    public static final String END_JOB_GROUP = "endLotteryGroup_";

}
