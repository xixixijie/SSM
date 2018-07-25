package com.ssm.model.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xixi on 2018/7/24.
 */
public class Auction {
    private int auctionID;
    private String auction_name;
    private String cover_url;
    private Timestamp beginDate;
    private Timestamp endDate;
    private int state;
    private int visited;
    private int wanted;
    private double begin_price;
    private double highest_price;
    private String introduction;
    private String begin;
    private String end;

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public String getAuction_name() {
        return auction_name;
    }

    public void setAuction_name(String auction_name) {
        this.auction_name = auction_name;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public Timestamp getBeginDate() {

        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {

        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getWanted() {
        return wanted;
    }

    public void setWanted(int wanted) {
        this.wanted = wanted;
    }

    public double getBegin_price() {
        return begin_price;
    }

    public void setBegin_price(double begin_price) {
        this.begin_price = begin_price;
    }

    public double getHighest_price() {
        return highest_price;
    }

    public void setHighest_price(double highest_price) {
        this.highest_price = highest_price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void change(){
        this.begin=beginDate.toString();
        this.end=endDate.toString();
        System.out.println(begin+" "+end);
    }
}
