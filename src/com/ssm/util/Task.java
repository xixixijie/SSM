package com.ssm.util;

import com.ssm.model.bean.Auction;
import com.ssm.model.dao.AuctionImp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


public class Task extends TimerTask {

    protected final Log logger = LogFactory.getLog(getClass());

    private static boolean isRunning = false;




    @Override
    public void run() {
        if (!isRunning) {
            isRunning = true;
            logger.info("开始执行任务。");


            List<Auction> list=new AuctionImp().getAuctions();

            for(Auction a:list){
                if(a.getEndDate().getTime()<=new Date().getTime()){
                    System.out.println(a.getAuction_name());
                    System.out.println("有拍卖结束");
                }
            }

            isRunning = false;
            logger.info("任务执行结束。");

        } else {
            logger.info("上一次任务执行还未结束，本次任务不能执行。");
        }

    }
}

