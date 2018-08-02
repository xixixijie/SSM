package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 *  <p>
 *      跟团记录实体类
 *  </p>
 *
 *  @author chenyufeng
 */
public class JoinGroupList {
    int joinID;                                 //跟团记录ID，用于唯一区分跟团记录
    Group group;                                //跟团记录对应的团购队伍，团购队伍的相关信息封装在Group对象中
    UserInfo joiner;                            //跟团人，跟团人的相关信息封装在UserInfo对象汇总
    Date joinDate;                              //跟团日期
    String receiverName;                        //收货人姓名
    String receiverTelephone;                   //收货人手机号
    int receiverPostCode;                       //收获邮编
    String receiverAddress;                     //具体收货地址
    int billNeeded;                             //是否需要纸质发票  0不需要  1需要

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

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