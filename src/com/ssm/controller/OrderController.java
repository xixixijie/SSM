package com.ssm.controller;

import com.ssm.model.bean.*;
import com.ssm.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    //根据用户id查询该用户的所有地址
 /*   @RequestMapping("selectAddress")
    public String selectAddress(Integer user_id, Model model){
        List<Address>  list=orderService.selectAddress(1);
        model.addAttribute("result2",list);
        //  session.setAttribute("result2",list);
        //return "redirect:selectAllProduct.action";
        return "order";
    }*/

    //根据商品ID查询所有选中的商品信息
    @RequestMapping("selectAllProduct")
    public String selectProduct(int[] productIds,Model model,Integer user_id,CarProduct carp){
        //修改商品数量
        orderService.updatePnumber(carp);

        List<Product> list=orderService.selectProduct(productIds);
        model.addAttribute("result3",list);

        //根据用户id查询该用户的所有地址
        List<Address>  list2=orderService.selectAddress(1);
        model.addAttribute("result2",list2);
        return "order";
    }



    //生成订单
 @RequestMapping("createOrder")
    public String insertOrder(OrderInfo order,Integer address_id,OrProduct orProduct){
        order=new OrderInfo();
        order.setPay_method("null");
        Date date1=new Date();   //得到当前电脑的日期时间
        if(!"".equals("date1")) {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                date1=sf.parse("date1");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();}
        }
        order.setAddress_id(address_id);
        order.setCreate_date(date1);
        order.setPay_date(date1);
        order.setOrder_type(1);
        orderService.insertOrder(order);

        //生成订单商品
         orderService.insertOrderProduct(orProduct);
         return "settlement";
    }

    //生成订单商品
  /*  @RequestMapping("createOrderProduct")
    public String insertOrderProduct(OrderProduct orderProduct){
        orderProduct=new OrderProduct();
        orderService.insertOrderProduct(orderProduct);
        return " ";
    }*/

    //更改订单状态
    @RequestMapping("updateOrder")
    public  String  updateOrder(OrderInfo order){
        order=new OrderInfo();
        order.setOrder_type(2);
        order.setPay_method("支付宝");
        orderService.updateOrder(order);
        return "success";
    }
}
