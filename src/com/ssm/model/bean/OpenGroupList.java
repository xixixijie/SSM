package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 * <p>开团记录实体类</p>
 *
 * @author chenyufeng
 */
public class OpenGroupList {
    int openID;                         //开团记录ID，用于唯一区别开团记录
    UserInfo leader;                    //开团人,开团人的相关信息封装在UserInfo对象汇总
    Date openDate;                      //开团日期
    String receiverName;                //收货人姓名
    String receiverTelephone;           //收货人手机号
    int receiverPostCode;               //收获邮编
    String receiverAddress;             //具体收货地址
    int billNeeded;                     //是否需要纸质发票  0不需要  1需要

    public UserInfo getLeader() {
        return leader;
    }

    public void setLeader(UserInfo leader) {
        this.leader = leader;
    }

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

