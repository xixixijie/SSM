package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.*;
import com.ssm.model.dao.AuctionDAO;
import com.ssm.model.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by xixi on 2018/7/24.
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionDAO auctionDAO;

    @Autowired
    private UserInfoDAO userdao;

    /**
     * 根据页码和大小 返回拍卖品list
     * @param pagenum
     * @param pageSize
     * @return
     */
    public List<Auction> getAuctions(int pagenum, int pageSize) {
        System.out.println("-----获得所有拍卖品Service-----");
        /* System.out.println(auctionDAO.getAuctions().size()); */
        //System.out.println("~~~"+pagenum+" "+pageSize);
        List<Auction> list=new ArrayList<>();
        Page<Auction> page= PageHelper.startPage(pagenum,pageSize);
        auctionDAO.getAuctions(new Timestamp(new Date().getTime()));
        list=page.getResult();
        return list;
    }

    /**
     *  根据拍卖品id 返回一个拍卖品
     * @param aid
     * @return
     */

    public Auction getAuction(int aid) {
        System.out.println("-----获得拍卖品Service-----");

        return auctionDAO.getAuction(aid);
    }

    /**
     * 根据拍卖品id返回 介绍图list
     * @param aid
     * @return
     */

    public List<AuctionPic> getAuctionPic(int aid) {
        System.out.println("-----获得轮播图Service-----");
        return auctionDAO.getAuctionPic(aid);
    }

    /**
     * 根据拍卖品id生成 拍卖品历史list
     * @param aid
     * @return
     */

    public List<History> getHistory(int aid) {
        System.out.println("-----获得历史Service-----");
        System.out.println(aid);
        List<History> list =auctionDAO.getHistory(aid);
        for(History h:list){
            h.setUsername(userdao.getUsername (h.getUserID()));
            h.change();
        }
        return list;

    }

    /**
     * 添加一条竞拍历史
     * @param userid
     * @param aid
     * @param price
     * @param wanted
     */

    public void addHistory(int userid, int aid, double price,int wanted) {
        System.out.println("-----添加历史Service-----");
        History history=new History();
        history.setUserID(userid);
        history.setAuctionID(aid);
        history.setPrice(price);
        history.setTime(new Timestamp(new Date().getTime()));
        auctionDAO.addHistory(history);
        //想要人数加一 更新最高价
        Map<String,Integer> map=new HashMap<>();
        map.put("aid",aid);
        map.put("wanted",wanted+1);
        map.put("price",(int)price);
        auctionDAO.addWanted(map);
    }

    /**
     * 根据用户id 获得拍卖品id
     * @param userID
     * @return
     */
    public List<AuctionOrder> getAuctionOrder(int userID) {
        System.out.println("-----获取拍卖订单Service-----");
        return auctionDAO.getAuctionOrder(userID);
    }

    /**
     * 添加拍卖品以及介绍图
     * @param auction
     * @param files
     */

    public void addAuction(Auction auction, String[] files) {
        System.out.println("-----添加拍卖商品Service-----");
        //获得最大id
        int maxID=auctionDAO.getMAXID();
        auction.setAuctionID(maxID);
        auctionDAO.addAuction(auction);

        //添加介绍图
        for(String str:files){
            AuctionPic auctionPic=new AuctionPic();
            auctionPic.setAuctionID(maxID);
            auctionPic.setPicName(str);
            auctionDAO.addAuctionPic(auctionPic);
        }
    }
}
