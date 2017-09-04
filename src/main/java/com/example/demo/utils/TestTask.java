package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by clb on 17-7-14.
 */

@Configurable
@EnableScheduling
public class TestTask {
	@Autowired
	ScheduleJobService scheduleJobService;
	/**
	 * 检查任务启动情况
	 */
	public void run() {
		System.out.println("测试任务线程开始执行");
	}
}
