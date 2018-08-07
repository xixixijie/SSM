package com.ssm.model.dao;

import com.ssm.model.bean.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface OrderDAO {
    //根据用户ID查询用户所有地址
    public List<Address> selectAddress(int user_id);

    //根据商品ID查询所有选中的商品信息
    public List<Product> selectProduct(int[] productIds);

    //生成订单
    public void  insertOrder(OrderInfo order);

    //拿到订单ID
    public  int  selectOrderId();

    //生成订单商品
    public void  insertOrderProduct(OrderProduct orderProduct);

    //修改购物车商品表中的商品数量
    public void  updatePnumber(CarProduct carp);


    //更改订单状态
    public void updateOrder(OrderInfo order);
}

