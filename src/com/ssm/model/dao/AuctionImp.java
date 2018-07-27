package com.ssm.model.dao;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;
import com.ssm.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuctionImp implements AuctionDAO {
    @Override
    public  List<Auction> getAuctions() {
        Connection conn=DBUtil.getConn();
        String sql="select * from auction where state=1";
        List<Auction> list=new ArrayList<>();
        try {
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

           while(rs.next()){
               Auction auction=new Auction();
               auction.setEndDate(rs.getTimestamp("enddate"));
               auction.setAuctionID(rs.getInt("auctionid"));
               auction.setAuction_name(rs.getString("auction_name"));

               list.add(auction);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Auction getAuction(int aid) {
        return null;
    }

    @Override
    public List<AuctionPic> getAuctionPic(int aid) {
        return null;
    }

    @Override
    public List<History> getHistory(int aid) {
        return null;
    }

    @Override
    public void addHistory(History history) {

    }

    @Override
    public void addWanted(Map<String, Integer> map) {

    }
}
