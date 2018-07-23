package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class Activity {
    int activityID;
    Product product;
    int requiredNumber;
    Date groupStartDate;
    Date groupEndDate;
    int activityStatus;
    double group_buying_price;
    boolean canDelete;

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
