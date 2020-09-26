package com.platform.utils.quartz;

import com.platform.service.ActivityLotteryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ztt
 * @Description
 * @Date 2020/9/25 0025 下午 4:07
 */
public class AcitivtyTask implements Job {

    @Autowired
    private ActivityLotteryService activityLotteryService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("yes ok");
        System.out.println("open prize in...."+activityLotteryService);

    }
}


