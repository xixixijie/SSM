package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 *
 * <p>团购活动实体类</p>
 */

public class Activity {
    int activityID;                             //团购活动ID，用于唯一区分团购活动
    Product product;                            //商品对象，用于封装该团购活动涉及的商品的信息
    int requiredNumber;                         //团购需要的参与人数
    Date groupStartDate;                        //团购开始时间
    Date groupEndDate;                          //团购结束时间
    int activityStatus;                         //团购状态    0被删除  1已经结束  2进行中  3还没开始
    double group_buying_price;                  //团购商品的团购价格
    boolean canDelete;                          //团购活动是否可以被删除
                                                //已经开始的团购活动不能删除，为false，
                                                //反之尚未开始的团购活动可以被删除，为true


    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public double getGroup_buying_price() {
        return group_buying_price;
    }

    public void setGroup_buying_price(double group_buying_price) {
        this.group_buying_price = group_buying_price;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRequiredNumber() {
        return requiredNumber;
    }

    public void setRequiredNumber(int requiredNumber) {
        this.requiredNumber = requiredNumber;
    }

    public Date getGroupStartDate() {
        return groupStartDate;
    }

    public void setGroupStartDate(Date groupStartDate) {
        this.groupStartDate = groupStartDate;
    }

    public Date getGroupEndDate() {
        return groupEndDate;
    }

    public void setGroupEndDate(Date groupEndDate) {
        this.groupEndDate = groupEndDate;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }
}
