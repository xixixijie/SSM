package com.ssm.model.bean;

import java.util.Date;

/**
 * Created by fouter on 2018/7/17.
 */
public class Product {
    private int product_id;
    private String product_name;
    private Classify classify;
    private double original_price;
    private double dicount_price;
    private String cover_url;
    private Date on_date;

    public Date getOn_date() {
        return on_date;
    }

    public void setOn_date(Date on_date) {
        this.on_date = on_date;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getDicount_price() {
        return dicount_price;
    }

    public void setDicount_price(double dicount_price) {
        this.dicount_price = dicount_price;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }


}
