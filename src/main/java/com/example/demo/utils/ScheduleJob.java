package com.example.demo.utils;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "task_quartz")
public class ScheduleJob {
	private Long id;
	private String jobId;
	private String jobName;
	private String jobGroup;
	private String jobDesc;
	private String jobStatus;
	private String cronExpression;
}
