package com.platform.utils.quartz;

import org.quartz.*;

public class QuartzUtils {

	/**
	 * 添加定时任务
	 * @param job,type(1售卖机任务，2免单活动任务,3-0元购更具时间进行执行,5-0元购作废)
	 * @throws Exception
	 */
	public static void addJob(Scheduler scheduler, ScheduleJobBean job, int type) throws Exception {
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		SimpleTrigger simpleTrigger1 = (SimpleTrigger) scheduler.getTrigger(triggerKey);
		JobDetail jobDetail = null;
		if(type == 1) {
			jobDetail = JobBuilder.newJob(AcitivtyTask.class).withIdentity(job.getJobName(), job.getJobGroup())
					.build();
		}
		//如果任务不存在添加
		if (simpleTrigger1== null) {
			SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup()).startAt(job.getStartTime()).build();
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		}
		//修改
		else {
			JobKey jobKey = JobKey.jobKey(job.getJobName(),job.getJobGroup());
			scheduler.deleteJob(jobKey);
			SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup()).startAt(job.getStartTime()).build();
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		}
		
	}

    /**
     * 暂停一个job
     * @param scheduleJob
     * @throws
     */
    public static void pauseJob(Scheduler scheduler,ScheduleJobBean scheduleJob) throws Exception{
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    	scheduler.pauseJob(jobKey);
    }
    
    /**
     * 恢复一个job
     * @param scheduleJob
     */
    public static void resumeJob(Scheduler scheduler,ScheduleJobBean scheduleJob) throws Exception{
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }
    
    /**
     * 删除一个任务
     * @param
     */
    public static void removeJob(Scheduler scheduler,ScheduleJobBean job) throws Exception{
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        JobKey jobKey = JobKey.jobKey(job.getJobName(),job.getJobGroup());
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
    }
	
}