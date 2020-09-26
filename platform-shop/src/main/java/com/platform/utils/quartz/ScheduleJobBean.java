package com.platform.utils.quartz;

import java.util.Date;

public class ScheduleJobBean {
	/** 任务id */
	private String jobId;
	/** 任务名称 */
	private String jobName;
	/** 任务分组 */
	private String jobGroup;
	/** 任务状态 0停止 1启用 2删除 */
	private String jobStatus;
	/** 任务运行时间表达式 */
	private Date startTime;
	/** 任务描述 */
	private String desc;
	/** 
     * 任务执行时调用哪个类的方法 包名+类名 
     */  
    private Class clazz;
    /** 
     * 任务调用的方法名 
     */  
    private String methodName;
    
    /**
	 * 得到该job的Trigger名字
	 * 
	 * @return
	 */
	public String getTriggerName() {
		return this.getJobId() + "Trigger";
	}
	
	public Class getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
