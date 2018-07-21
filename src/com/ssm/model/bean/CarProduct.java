package com.ssm.model.bean;

import java.util.Date;

public class CarProduct {
    private int carProduct_id;
    private int product_id;
    private int user_id;
    private Date create_date;
    public int getCarProduct_id() {
        return carProduct_id;
    }

    public void setCarProduct_id(int carProduct_id) {
        this.carProduct_id = carProduct_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }


}
