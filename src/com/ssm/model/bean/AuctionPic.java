package com.ssm.model.bean;

public class AuctionPic {
    private int auctionpicID;
    private int auctionID;
    private int picName;

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

    public int getPicName() {
        return picName;
    }

    public void setPicName(int picName) {
        this.picName = picName;
    }
}
