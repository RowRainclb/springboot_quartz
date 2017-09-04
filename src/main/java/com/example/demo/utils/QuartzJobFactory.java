package com.example.demo.utils;

import com.example.demo.service.TaskService;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 *
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 */
@Component
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行");
		ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
		TaskService taskService = MyApplicationContextUtil.getBean("taskService");
		try {
			taskService.run(Long.parseLong(scheduleJob.getJobName()));
		} catch (Exception e) {
//			log.error("定时任务运行出错！",e);
			System.out.println("定时任务运行出错"+e.getMessage());
			ScheduleJobService scheduleJobService = MyApplicationContextUtil.getBean("scheduleJobService");
			try {
//				log.debug("删除定时任务");
				System.out.println("删除定时任务");
				scheduleJobService.removeJob(scheduleJob);
				System.out.println("删除数据库任务记录");
				//删除数据库中记录
//				TaskQuartzMapper taskQuartzMapper= MyApplicationContextUtil.getBean("taskQuartzMapper");
//				taskQuartzMapper.delete(scheduleJob);
			} catch (SchedulerException e1) {
//				log.error("删除定时任务出错！",e);
				System.out.println("删除定时任务出错！"+e.getMessage());
			}
		}
	}
}
