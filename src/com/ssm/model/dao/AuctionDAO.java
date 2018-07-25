package com.ssm.model.dao;

import com.ssm.model.bean.Auction;

import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
public interface AuctionDAO {
    public List<Auction> getAuctions();

    Auction getAuction(int aid);
}
