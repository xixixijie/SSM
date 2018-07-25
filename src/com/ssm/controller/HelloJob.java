package com.ssm.controller;

import org.quartz.*;

/**
 * Created by chenyufeng on 2018/7/25.
 */
public class HelloJob  implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myVaule = dataMap.getFloat("myVaule");

        System.out.println(jobSays+myVaule);
    }
}
