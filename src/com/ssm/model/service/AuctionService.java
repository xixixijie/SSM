package com.ssm.model.service;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionOrder;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;
import com.ssm.model.dao.AuctionDAO;
import com.ssm.model.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xixi on 2018/7/24.
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionDAO dao;

    @Autowired
    private UserInfoDAO userdao;

    public List<Auction> getAuctions() {
        System.out.println("-----获得所有拍卖品Service-----");
        //System.out.println(dao.getAuctions().size());
        return dao.getAuctions();
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
}
