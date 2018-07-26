package com.ssm.model.dao;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;

import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
public interface AuctionDAO {
    public List<Auction> getAuctions();

    Auction getAuction(int aid);

    List<AuctionPic> getAuctionPic(int aid);

    List<History> getHistory(int aid);

    void addHistory(History history);
}
