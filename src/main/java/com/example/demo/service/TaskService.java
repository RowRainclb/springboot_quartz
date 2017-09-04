package com.example.demo.service;

import com.example.demo.utils.ScheduleJob;
import com.example.demo.utils.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clb
 */
@Service
public class TaskService {
    @Autowired
    private ScheduleJobService scheduleJobService;
    /**
     * 定时任务执行内容
     * 启动任务
     * @param taskId 任务id
     * @return
     * @throws Exception
     */
    public Long run(Long taskId) throws Exception {
        try{
            System.out.println("进入定时任务－－－");
            System.out.println("------");
            System.out.println("本次任务执行完毕－－－");
            return null;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    /**
     * 自动任务
     * @param taskId
     * @throws Exception
     */
    public Long autoTask (Long taskId) throws Exception {
        String cron = String.format("0 0/%s * * * ? *",30);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobDesc(String.format("task(id:%s) is running",taskId));
        scheduleJob.setJobName(String.valueOf(taskId));
        scheduleJob.setJobGroup("test_group");
        scheduleJob.setCronExpression(cron);
        scheduleJobService.addScheduleJob(scheduleJob);
//        log.debug("定时任务保存记录");
//        List<ScheduleJob> list = taskQuartzMapper.queryList(scheduleJob);
//        if(list.size() == 0){
//            taskQuartzMapper.insertRecord(scheduleJob);
//        }
        return taskId;
    }

}
