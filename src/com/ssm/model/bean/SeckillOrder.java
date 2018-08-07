package com.ssm.model.bean;

import java.sql.Timestamp;

public class SeckillOrder {
    private int seckillOrder_id;
    private int seckillProduct_id;
    private Timestamp order_time;
    private int address_id;
    private int user_id;
    private SeckillProduct seckillProduct;
    private Address address;
    private UserInfo userInfo;

    public int getSeckillOrder_id() {
        return seckillOrder_id;
    }

    public void setSeckillOrder_id(int seckillOrder_id) {
        this.seckillOrder_id = seckillOrder_id;
    }

    public int getSeckillProduct_id() {
        return seckillProduct_id;
    }

    public void setSeckillProduct_id(int seckillProduct_id) {
        this.seckillProduct_id = seckillProduct_id;
    }

    public Timestamp getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Timestamp order_time) {
        this.order_time = order_time;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public SeckillProduct getSeckillProduct() {
        return seckillProduct;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
