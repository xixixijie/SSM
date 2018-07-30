package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.*;
import com.ssm.model.dao.AuctionDAO;
import com.ssm.model.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by xixi on 2018/7/24.
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionDAO dao;

    @Autowired
    private UserInfoDAO userdao;

    public List<Auction> getAuctions(int pagenum, int pageSize) {
        System.out.println("-----获得所有拍卖品Service-----");
        /* System.out.println(dao.getAuctions().size()); */
        System.out.println("~~~"+pagenum+" "+pageSize);
        List<Auction> list=new ArrayList<>();
        Page<Auction> page= PageHelper.startPage(pagenum,pageSize);
        dao.getAuctions(new Timestamp(new Date().getTime()));
        list=page.getResult();
        return list;
    }

    public Auction gAuction(int aid) {
        System.out.println("-----获得拍卖品Service-----");

        return dao.getAuction(aid);
    }

    public List<AuctionPic> gAuctionPic(int aid) {
        System.out.println("-----获得轮播图Service-----");
        return dao.getAuctionPic(aid);
    }

    public List<History> gHistory(int aid) {
        System.out.println("-----获得历史Service-----");
        System.out.println(aid);
        List<History> list =dao.getHistory(aid);
        for(History h:list){
            h.setUsername(userdao.getUsername (h.getUserID()));
            h.change();
        }
        return list;

    }

    public void addHistory(int userid, int aid, double price,int wanted) {
        System.out.println("-----添加历史Service-----");
        History history=new History();
        history.setUserID(userid);
        history.setAuctionID(aid);
        history.setPrice(price);
        history.setTime(new Timestamp(new Date().getTime()));
        dao.addHistory(history);
        //想要人数加一
        Map<String,Integer> map=new HashMap<>();
        map.put("aid",aid);
        map.put("wanted",wanted+1);
        map.put("price",(int)price);
        dao.addWanted(map);
    }

    public List<AuctionOrder> getAuctionOrder() {
        System.out.println("-----获取拍卖订单Service-----");
        return dao.getAuctionOrder();
    }

    public void addAuction(Auction auction, String[] files) {
        System.out.println("-----添加拍卖商品Service-----");
        //获得最大id
        int maxID=dao.getMAXID();
        auction.setAuctionID(maxID);
        dao.addAuction(auction);

        //添加介绍图
        for(String str:files){
            AuctionPic auctionPic=new AuctionPic();
            auctionPic.setAuctionID(maxID);
            auctionPic.setPicName(str);
            dao.addAuctionPic(auctionPic);
        }
    }
}
