package com.ssm.model.service;
import com.ssm.model.bean.*;
import com.ssm.model.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;
    //根据用户id查询该用户的所有地址
    public List<Address> selectAddress(int user_id){
        return  orderDAO.selectAddress(user_id);
    }

    //根据商品ID查询所有选中的商品信息
    public List<Product> selectProduct(int[] productIds){
        return orderDAO.selectProduct(productIds);
    }

    //修改购物车商品表中的商品数量
    public void updatePnumber(CarProduct carp){
        orderDAO.updatePnumber(carp);
    }

    //根据购物车商品ID查询该商品的数量
   /* public List<CarProduct> selectCarProductNumber(int carProduct_id){
        return  orderDAO.selectCarProductNumber(carProduct_id);
    }*/


    //生成订单
    public void insertOrder(OrderInfo order){
        orderDAO.insertOrder(order);
    }



    //生成订单商品
    //List<OrderProduct> orderProducts
    public void insertOrderProduct(OrProduct orProduct){
        //得到orderId
        int order_id=orderDAO.selectOrderId();

        Date date=new Date();   //得到当前电脑的日期时间
        if(!"".equals("date1")) {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                date=sf.parse("date1");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();}
        }
        for(OrderProduct orderProduct:orProduct.getOrderProducts()){
             orderProduct.setOrder_id(order_id);
             orderProduct.setCreate_date(date);
             orderDAO.insertOrderProduct(orderProduct);
         }
    }

    //更改订单状态
    public void updateOrder(OrderInfo order){
        orderDAO.updateOrder(order);
    }
}

