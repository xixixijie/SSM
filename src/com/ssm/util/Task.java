package com.ssm.util;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.History;
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
            AuctionImp dao=new AuctionImp();

            List<Auction> list=dao.getAuctions();

            for(Auction a:list){
                if(a.getEndDate().getTime()<=new Date().getTime()){
                    if(a.getWanted()<1){
                        //流拍
                        dao.Abortive(a.getAuctionID());
                    }else{
                        //生成订单
                        List<History> histories=dao.getHistory(a.getAuctionID());
                        //获取关于该商品的最高竞拍记录
                        History history=histories.get(0);
                        //生成通知消息
                        String title="竞拍成功";
                        String body="您的竞拍"+a.getAuction_name()+"成功"+",成交价"+history.getPrice()+"元";
                        System.out.println(title+" "+body);
                        dao.addMessage(history.getUserID(),title,body);


                    }
                }
            }

            isRunning = false;
            logger.info("任务执行结束。");

        } else {
            logger.info("上一次任务执行还未结束，本次任务不能执行。");
        }

    }
}

