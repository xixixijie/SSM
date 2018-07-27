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
                auction.setWanted(rs.getInt("wanted"));
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
        Connection conn=DBUtil.getConn();
        String sql="select * from HISTORY where AUCTIONID="+aid;
        List<History> list=new ArrayList<>();
        try {
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                History history=new History();
                history.setUserID(rs.getInt("userid"));
                history.setPrice(rs.getDouble("price"));
                history.setAuctionID(rs.getInt("auctionid"));
                list.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void addHistory(History history) {

    }

    @Override
    public void addWanted(Map<String, Integer> map) {

    }

    public void Abortive(int auctionID) {
        Connection conn=DBUtil.getConn();
        String sql="update auction set state=3 where AUCTIONID="+auctionID;

        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addMessage(int userID, String title, String body) {
        Connection conn=DBUtil.getConn();
        int maxid=0;
        String sql1="select nvl(max(messageID),0)+1 as maxid from user_message";
        String sql2="insert into USER_MESSAGE values(?,?,?,?,0)";
        try {
            PreparedStatement statement=conn.prepareStatement(sql1);
            ResultSet rs=statement.executeQuery();
            if(rs.next())
                maxid=rs.getInt("maxid");
            statement=conn.prepareStatement(sql2);
            statement.setInt(1,maxid);
            statement.setInt(2,userID);
            statement.setString(3,title);
            statement.setString(4,body);

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
