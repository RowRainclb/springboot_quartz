package com.example.demo;

/**
 * Created by clb on 17-7-17.
 */

import com.example.demo.utils.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 */
@Component
public class MyStartupRunner1 implements CommandLineRunner {
	@Autowired
	ScheduleJobService scheduleJobService;
	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
		init();
	}

	/**
	 * 在项目启动时运行以下代码
	 * @throws Exception
	 */
	public void init() throws Exception {
		scheduleJobService.getAllSchedules();
	}
}
