package com.ssm.model.dao;


import com.ssm.model.bean.*;
import com.ssm.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuctionImp implements AuctionDAO {
    @Override
    public  List<Auction> getAuctions(Timestamp date) {
        Connection conn=DBUtil.getConn();
        String sql="select * from auction where state=1 ";
        List<Auction> list=new ArrayList<>();
        try {
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

           while(rs.next()){
               Auction auction=new Auction();
               auction.setEndDate(rs.getTimestamp("enddate"));
               auction.setAuctionID(rs.getInt("auctionid"));
               auction.setAuction_name(rs.getString("auction_name"));
               auction.setCover_url(rs.getString("cover_url"));
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
        String sql="select * from HISTORY where AUCTIONID="+aid +" order by price desc ";
        List<History> list=new ArrayList<>();
        try {
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                History history=new History();
                history.setUserID(rs.getInt("userid"));
                history.setPrice(rs.getDouble("price"));
                history.setAuctionID(rs.getInt("auctionid"));
                history.setUsername(getUsername(history.getUserID()));
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

    @Override
    public List<AuctionOrder> getAuctionOrder(int userID) {
        return null;
    }

    @Override
    public int getMAXID() {
        return 0;
    }

    @Override
    public void addAuction(Auction auction) {

    }

    @Override
    public void addAuctionPic(AuctionPic auctionPic) {

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

    public void modifyAuction(int auctionID) {
        Connection connection=DBUtil.getConn();
        String sql="update AUCTION set STATE=2 where AUCTIONID="+auctionID;

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(int userid){
        Connection connection=DBUtil.getConn();
        String sql="select username from userinfo where user_id="+userid;
        String name="";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                name=rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void addAuctionOrder(AuctionOrder auctionOrder) {
        Connection connection=DBUtil.getConn();
        String sql1="select nvl(max(AOID),0)+1 as maxid from AUCTIONORDER";
        String sql2="select * from ACCEPT_ADDRESS where USER_ID="+auctionOrder.getUserid();
        String sql3="insert into AUCTIONORDER values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int maxid=-1;

        Address address=new Address();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql1);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                maxid= resultSet.getInt("maxid");
            }
            preparedStatement=connection.prepareStatement(sql2);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                address.setAddress_ID(resultSet.getInt("address_id"));
                address.setAddress(resultSet.getString("address"));
                address.setPostcode(resultSet.getInt("postcode"));
                address.setAccept_Phone(resultSet.getLong("accept_phone"));
                address.setAccept_Name(resultSet.getString("accept_name"));
            }
            preparedStatement=connection.prepareStatement(sql3);
            preparedStatement.setInt(1,maxid);
            preparedStatement.setInt(2,auctionOrder.getAuctionid());
            preparedStatement.setInt(3,auctionOrder.getUserid());
            preparedStatement.setString(4,auctionOrder.getUsername());
            preparedStatement.setDouble(5,auctionOrder.getPrice());
            preparedStatement.setTimestamp(6,auctionOrder.getDate());
            preparedStatement.setInt(7,auctionOrder.getState());
            preparedStatement.setString(8,auctionOrder.getAuction_name());
            preparedStatement.setString(9,address.getAddress());
            preparedStatement.setInt(10,address.getPostcode());
            preparedStatement.setLong(11,address.getAccept_Phone());
            preparedStatement.setString(12,address.getAccept_Name());
            preparedStatement.setString(13,auctionOrder.getCover_url());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
