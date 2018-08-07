package com.ssm.model.dao;

import com.ssm.model.bean.CarProduct;

import java.util.List;

public interface CarProductDAO {
    //向购物车中添加商品
    public void insertCarProduct(CarProduct carp);
    //根据用户ID查询购物车中商品
    public  List<CarProduct> selectCarProduct(int user_id);
    //根据购物车商品ID删除购物车中的商品
    public  void  deleteCarProduct(int carProduct_id);
}
