package com.ssm.model.dao;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionOrder;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by xixi on 2018/7/24.
 */
public interface AuctionDAO {
    public List<Auction> getAuctions(Timestamp date);

    Auction getAuction(int aid);

    List<AuctionPic> getAuctionPic(int aid);

    List<History> getHistory(int aid);

    void addHistory(History history);

    void addWanted(Map<String,Integer> map);

    List<AuctionOrder> getAuctionOrder();

    int getMAXID();

    void addAuction(Auction auction);

    void addAuctionPic(AuctionPic auctionPic);
}
