package com.ssm.model.bean;

public class Remind {
    private int remind_id;
    private int user_id;
    private int seckillproduct_id;

    private SeckillProduct seckillProduct;

    public SeckillProduct getSeckillProduct() {
        return seckillProduct;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }

    public int getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(int remind_id) {
        this.remind_id = remind_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSeckillproduct_id() {
        return seckillproduct_id;
    }

    public void setSeckillproduct_id(int seckillproduct_id) {
        this.seckillproduct_id = seckillproduct_id;
    }
}
