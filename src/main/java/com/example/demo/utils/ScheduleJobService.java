package com.example.demo.utils;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clb on 17-7-17.
 */
@Service
public class ScheduleJobService {
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	private static String JOB_GROUP_NAME = "JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";



	public void getAllSchedules() throws Exception {
		// 这里从数据库中获取任务信息数据
		List<ScheduleJob> jobList = new ArrayList<>();
		ScheduleJob scheduleJob = new ScheduleJob();
		String cron = String.format("0 0/%s * * * ? *",1);
		scheduleJob.setJobDesc(String.format("task(id:%s) is running","taskId"));
		scheduleJob.setJobName(String.valueOf(11));
		scheduleJob.setJobGroup("test_group");
		scheduleJob.setCronExpression(cron);
		jobList.add(scheduleJob);
		for (ScheduleJob job : jobList) {
			addScheduleJob(job);
		}
	}
	/**
	 * @Description: 添加一个定时任务
	 * @param job
	 * @throws SchedulerException
	 */

	public void addScheduleJob(ScheduleJob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		//不存在，创建一个
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
			//按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().startNow().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().startNow().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			//按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
		System.out.println("触发器执行完毕！");
		// 启动
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
	}
	/**
	 * @Description: 移除一个任务
	 * @param job
	 * @throws SchedulerException
	 */
	public void removeJob(ScheduleJob job) throws SchedulerException {
		Scheduler sched = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		sched.pauseTrigger(triggerKey);// 停止触发器
		sched.unscheduleJob(triggerKey);// 移除触发器
		JobKey jobKey = new JobKey(job.getJobName(),job.getJobGroup());
		sched.deleteJob(jobKey );// 删除任务
	}
}
