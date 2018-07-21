package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class OpenGroupList {
    int openID;
    Date openDate;
    String receiverName;
    String receiverTelephone;
    int receiverPostCode;
    String receiverAddress;
    int billNeeded;

    public int getOpenID() {
        return openID;
    }

    public void setOpenID(int openID) {
        this.openID = openID;
    }


    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
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

