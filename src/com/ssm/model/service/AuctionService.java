package com.ssm.model.service;

import com.ssm.model.bean.Auction;
import com.ssm.model.dao.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionDAO dao;

    public List<Auction> getAuctions() {
        System.out.println("-----获得所有拍卖品Service-----");
        //System.out.println(dao.getAuctions().size());
        return dao.getAuctions();
    }

    public Auction gAuction(int aid) {
        System.out.println("-----获得拍卖品Service-----");
        return dao.getAuction(aid);
    }
}
