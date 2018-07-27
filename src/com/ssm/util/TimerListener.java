package com.ssm.util;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TimerListener implements ServletContextListener{
    private Timer timer = null;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        timer = new Timer(true);
        event.getServletContext().log("定时器已启动");

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的11:00:00执行
        calendar.set(year, month, day, 10, 07, 00);
        java.util.Date date = calendar.getTime();
        int period = 30 * 1000;
        //每天的date时刻执行TestTask，每隔30秒重复执行
        timer.schedule(new Task(), date, period);
        //每天的date时刻执行TestTask, 仅执行一次
        //timer.schedule(new TestTask(), date);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (timer != null) {
            timer.cancel();
            event.getServletContext().log("定时器销毁");
        }
    }
}
