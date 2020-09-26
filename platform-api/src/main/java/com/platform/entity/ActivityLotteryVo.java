package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.utils.DateUtils;
import lombok.Data;
import org.apache.poi.ss.usermodel.DateUtil;

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
public class ActivityLotteryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer placesNum;
    private Integer prizeType;
    private Integer prizeId;
    private String prizeName;
    private Date onlineTime;
    private Date downlineTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date openTime;
    private Date createTime;
    private Integer lotteryStatus;
    private Integer status;

}
