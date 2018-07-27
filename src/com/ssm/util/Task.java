package com.ssm.util;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionOrder;
import com.ssm.model.bean.History;
import com.ssm.model.dao.AuctionImp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
            //System.out.println("auction大小"+list.size());
            for(Auction a:list){

//                System.out.println(a.getAuction_name());
//                System.out.println(a.getEndDate().getTime()<new Date().getTime());
//                System.out.println(a.getWanted());
//                System.out.println(a.getAuctionID());
                if(a.getEndDate().getTime()<=new Date().getTime()){
                    if(a.getWanted()<1){
                        //流拍
                        dao.Abortive(a.getAuctionID());
                    }else{
                        //生成订单
                        List<History> histories=dao.getHistory(a.getAuctionID());
                        //获取关于该商品的最高竞拍记录
                        //System.out.println("记录大小"+histories.size());
                        if(histories.size()>0){
                            History history=histories.get(0);
                            //生成通知消息
                            String title="竞拍成功";
                            String body="竞拍"+a.getAuction_name()+"成功"+",成交价"+history.getPrice()+"元";
//                            System.out.println(title+" "+body);
                            dao.addMessage(history.getUserID(),title,body);

                            //生成订单
                            AuctionOrder auctionOrder=new AuctionOrder();
                            auctionOrder.setAuctionid(a.getAuctionID());
                            auctionOrder.setAuction_name(a.getAuction_name());
                            auctionOrder.setDate(new Timestamp(new Date().getTime()));
                            auctionOrder.setUserid(history.getUserID());
                            auctionOrder.setPrice(history.getPrice());
                            auctionOrder.setState(1);
                            auctionOrder.setUsername(history.getUsername());
                            dao.addAuctionOrder(auctionOrder);
                            //System.out.println("添加订单");
                            //修改拍卖品状态
                            dao.modifyAuction(a.getAuctionID());
                            //其余的通知失败
                            List<Integer> userids=new ArrayList<>();
                            for(int i=1;i<histories.size();i++){
                                History h=histories.get(i);
                                if(userids.contains(h.getUserID()))
                                    continue;
                                userids.add(h.getUserID());
                                String title1="竞拍失败";
                                String body1="关于"+a.getAuction_name()+"的竞拍失败";
                                dao.addMessage(h.getUserID(),title1,body1);

                            }

                        }

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

