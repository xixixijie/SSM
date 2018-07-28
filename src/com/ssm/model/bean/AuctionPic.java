package com.ssm.model.bean;

public class AuctionPic {
    private int auctionpicID;
    private int auctionID;
    private String picName;

    public int getAuctionpicID() {
        return auctionpicID;
    }

    public void setAuctionpicID(int auctionpicID) {
        this.auctionpicID = auctionpicID;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
}
