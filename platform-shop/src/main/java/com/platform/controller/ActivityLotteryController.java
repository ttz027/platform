package com.platform.controller;

import com.platform.entity.ActivityLotteryEntity;
import com.platform.service.ActivityLotteryService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;
import com.platform.utils.quartz.QuartzUtils;
import com.platform.utils.quartz.ScheduleJobBean;
import com.platform.utils.quartz.SignUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@RestController
@RequestMapping("lottery")
public class ActivityLotteryController {
    @Autowired
    private ActivityLotteryService lotteryService;
    @Autowired
    private Scheduler scheduler;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("lottery:list")
    public R list(@RequestParam Map<String, Object> params) {
        /*LocalDate today = LocalDate.now();
        System.out.println("当月签到情况：");
        Map<String, Boolean> signInfo = new TreeMap<>(SignUtil.getSignInfo(1000, today));
        for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
        }*/
        //查询列表数据
        Query query = new Query(params);

        List<ActivityLotteryEntity> adList = lotteryService.queryList(query);
        int total = lotteryService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(adList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("lottery:info")
    public R info(@PathVariable("id") Integer id) {
        ActivityLotteryEntity ad = lotteryService.queryObject(id);

        return R.ok().put("ad", ad);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("lottery:save")
    public R save(@RequestBody ActivityLotteryEntity ad) {
        try {
            lotteryService.save(ad);
            createZeroTimeJob(ad);

            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("eroro:in ActivityLotteryController/save");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("lottery:update")
    public R update(@RequestBody ActivityLotteryEntity ad) {
        try {
            lotteryService.update(ad);
            createZeroTimeJob(ad);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("eroro:in ActivityLotteryController/update");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("lottery:delete")
    public R delete(@RequestBody Integer[] ids) {
        try{
            lotteryService.deleteBatch(ids);
            for (Integer id:ids) {
                ScheduleJobBean getjob = getjob(id);
                QuartzUtils.removeJob(scheduler,getjob);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("eroro:in ActivityLotteryController/delete");
        }
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<ActivityLotteryEntity> list = lotteryService.queryList(params);

        return R.ok().put("list", list);
    }

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("lotteryStatus",0);
            List<ActivityLotteryEntity> activityLotteryEntities = lotteryService.queryList(map);
            for (ActivityLotteryEntity b:activityLotteryEntities) {
                createZeroTimeJob(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("eroro:in ActivityLotteryController/init");
        }
    }

    public void createZeroTimeJob(ActivityLotteryEntity zeroBuy) throws Exception {
        ScheduleJobBean startJob = getjob(zeroBuy.getId());
        startJob.setStartTime(zeroBuy.getOpenTime());
        QuartzUtils.addJob(scheduler,startJob, 1);
    }
     private ScheduleJobBean getjob(int id) {
         ScheduleJobBean job = new ScheduleJobBean();
         job.setJobName(ActivityLotteryEntity.START_JOB_NAME + id);
         job.setJobGroup(ActivityLotteryEntity.START_JOB_GROUP + id);
         return job;
     }
}
