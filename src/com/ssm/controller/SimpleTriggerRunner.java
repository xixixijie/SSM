package com.ssm.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by chenyufeng on 2018/7/25.
 */
public class SimpleTriggerRunner {
    public static void main(String[] args) throws InterruptedException {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail jobDetail = newJob(HelloJob.class)
                    .withIdentity("job1","group1")
                    .usingJobData("jobSays","Hello!")
                    .usingJobData("myVaule",123f)
                    .build();

            JobDetail jobDetail2 = newJob(HelloJob.class)
                    .withIdentity("job2","group1")
                    .usingJobData("jobSays","Hello!")
                    .usingJobData("myVaule",234f)
                    .build();


            Date date = null;
            try {
                String times = "2018-07-26";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(times);
                System.out.println(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Date date2 = null;
            try {
                String times = "2018-07-27";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date2 = sdf.parse(times);
                System.out.println(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity("trigger1","group1")
                    .startAt(date)
                    .forJob("job1","group1")
                    .withSchedule(
                            simpleSchedule().withMisfireHandlingInstructionFireNow()
                    )
                    .build();


            SimpleTrigger trigger2 = (SimpleTrigger) newTrigger()
                    .withIdentity("trigger2","group1")
                    .startAt(date2)
                    .forJob("job2","group1")
                    .withSchedule(
                            simpleSchedule().withMisfireHandlingInstructionFireNow()
                    )
                    .build();


            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.scheduleJob(jobDetail2,trigger2);

//            Thread.sleep(60000);
//            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
