package com.ssm.model.bean;

import java.sql.Timestamp;

public class SeckillProduct {
    private int seckillproduct_id;
    private int product_id;
    private Timestamp start_time;
    private int all_amount;
    private double seckill_price;
    private Product product;
    private boolean canDelete;

    public int getSeckillproduct_id() {
        return seckillproduct_id;
    }

    public void setSeckillproduct_id(int seckillproduct_id) {
        this.seckillproduct_id = seckillproduct_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public int getAll_amount() {
        return all_amount;
    }

    public void setAll_amount(int all_amount) {
        this.all_amount = all_amount;
    }

    public double getSeckill_price() {
        return seckill_price;
    }

    public void setSeckill_price(double seckill_price) {
        this.seckill_price = seckill_price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
