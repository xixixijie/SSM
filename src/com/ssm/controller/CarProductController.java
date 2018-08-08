package com.ssm.controller;

import com.ssm.model.bean.CarProduct;
import com.ssm.model.service.CarProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CarProductController {

    @Autowired
    private CarProductService carProductService;

    //向购物车中添加商品
    @RequestMapping("addProduct")
    public String insertCarProduct(CarProduct carp, HttpServletRequest request){
      /*  Cookie cookies[]=request.getCookies();
        if(cookies !=null && cookies.length>0){
            for(int i=0;i<cookies.length;i++){
                Cookie sCookie=cookies[i];
                if(sCookie.getName().equals("productid")){
                  pid=sCookie.getValue();
                }*/
        Cookie myCookie[]=request.getCookies();//创建一个Cookie对象数组
        int userId=1;
        for(int n=0;n<myCookie.length-1;n++) {
            Cookie newCookie = myCookie[n];
            if(newCookie.getName().equals("miUserId")){
                System.out.println("@!@@@@@@"+newCookie.getValue());
                userId=Integer.parseInt(newCookie.getValue());

            }
        }


//        carp.setProduct_id(10);
        carp.setUser_id(userId);
        carp.setCpnumber(1);
        Date date1=new Date();
        if(!"".equals("date1")) {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                date1=sf.parse("date1");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();}
        }

        carp.setCreate_date(date1);

        carProductService.insertCarProduct(carp);

        return "car-success";
    }


    //根据用户Id查询购物车中商品
    @RequestMapping("selectProduct")
    public String selectCarProduct( Model model,HttpServletRequest request){

        Cookie myCookie[]=request.getCookies();//创建一个Cookie对象数组

        int userId=1;
        for(int n=0;n<myCookie.length-1;n++) {
            Cookie newCookie = myCookie[n];
            if(newCookie.getName().equals("miUserId")){
                System.out.println("@!@@@@@@"+newCookie.getValue());
                userId=Integer.parseInt(newCookie.getValue());

            }
        }


        List<CarProduct> list=carProductService.selectCarProduct(userId);
        model.addAttribute("result1",list);
        return "car";
    }

    //根据购物车商品ID删除购物车中的商品
    @RequestMapping("deleteCarProduct")
    public String  deleteCarProduct(Integer carProduct_id){
        carProductService.deleteCarProduct(carProduct_id);
        return "redirect:selectProduct.action";
    }


}
