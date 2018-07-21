package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class JoinGroupList {
    int joinID;
    UserInfo joiner;
    Date joinDate;
    String receiverName;
    String receiverTelephone;
    int receiverPostCode;
    String receiverAddress;
    int billNeeded;

    public int getJoinID() {
        return joinID;
    }

    public void setJoinID(int joinID) {
        this.joinID = joinID;
    }

    public UserInfo getJoiner() {
        return joiner;
    }

    public void setJoiner(UserInfo joiner) {
        this.joiner = joiner;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTelephone() {
        return receiverTelephone;
    }

    public void setReceiverTelephone(String receiverTelephone) {
        this.receiverTelephone = receiverTelephone;
    }

    public int getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(int receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public int getBillNeeded() {
        return billNeeded;
    }

    public void setBillNeeded(int billNeeded) {
        this.billNeeded = billNeeded;
    }
}