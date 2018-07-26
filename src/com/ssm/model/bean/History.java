package com.ssm.model.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xixi on 2018/7/24.
 */
public class History {
    private int HistoryID;
    private int userID;
    private int username;
    private double price;
    private int auctionID;
    private Timestamp time;
    private String t;

    public int getHistoryID() {
        return HistoryID;
    }

    public void setHistoryID(int historyID) {
        HistoryID = historyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void change(){
        this.t=time.toString();
    }
}
