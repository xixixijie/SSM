package com.ssm.controller;

import com.ssm.model.bean.*;
import com.ssm.model.service.BuySeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class BuySeckillController {

    @Autowired
    private BuySeckillService service;
    //加载秒杀时间
    @RequestMapping("getTime")
    @ResponseBody
    public List<TimeInfo> getTime(){
        List<TimeInfo> list = new ArrayList<TimeInfo>();
        //获取当前时间
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String yest="昨日";
        String toda="今日";
        String tomo="明日";
        TimeInfo t1 = new TimeInfo();
        TimeInfo t2 = new TimeInfo();
        TimeInfo t3 = new TimeInfo();
        TimeInfo t4 = new TimeInfo();
        if(hour<8){
            t1.setTime(yest+" 22:00:00");
            t1.setState("正在进行");
            list.add(t1);
            t2.setTime(toda+" 08:00:00");
            t2.setState("即将开始");
            list.add(t2);
            t3.setTime(toda+" 12:00:00");
            t3.setState("即将开始");
            list.add(t3);
            t4.setTime(toda+" 18:00:00");
            t4.setState("即将开始");
            list.add(t4);
        }else if(hour>=8&&hour<12){
            t1.setTime(toda+" 08:00:00");
            t1.setState("正在进行");
            list.add(t1);
            t2.setTime(toda+" 12:00:00");
            t2.setState("即将开始");
            list.add(t2);
            t3.setTime(toda+" 18:00:00");
            t3.setState("即将开始");
            list.add(t3);
            t4.setTime(toda+" 22:00:00");
            t4.setState("即将开始");
            list.add(t4);
        }else if(hour>=12&&hour<18){
            t1.setTime(toda+" 12:00:00");
            t1.setState("正在进行");
            list.add(t1);
            t2.setTime(toda+" 18:00:00");
            t2.setState("即将开始");
            list.add(t2);
            t3.setTime(toda+" 22:00:00");
            t3.setState("即将开始");
            list.add(t3);
            t4.setTime(tomo+" 08:00:00");
            t4.setState("即将开始");
            list.add(t4);
        }else if(hour>=18&&hour<22){
            t1.setTime(toda+" 18:00:00");
            t1.setState("正在进行");
            list.add(t1);
            t2.setTime(toda+" 22:00:00");
            t2.setState("即将开始");
            list.add(t2);
            t3.setTime(tomo+" 08:00:00");
            t3.setState("即将开始");
            list.add(t3);
            t4.setTime(tomo+" 12:00:00");
            t4.setState("即将开始");
            list.add(t4);
        }else {
            t1.setTime(toda+" 22:00:00");
            t1.setState("正在进行");
            list.add(t1);
            t2.setTime(tomo+" 08:00:00");
            t2.setState("即将开始");
            list.add(t2);
            t3.setTime(tomo+" 12:00:00");
            t3.setState("即将开始");
            list.add(t3);
            t4.setTime(tomo+" 18:00:00");
            t4.setState("即将开始");
            list.add(t4);
        }
        return list;
    }

    //加载秒杀商品
    @RequestMapping("/getSeckillProducts/{t}")
    @ResponseBody
    public List<SeckillProduct> getSeckillProducts(@PathVariable String t){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int search=Integer.parseInt(t);
        //构建时间，日期数组
        String time[]={"08:00:00","12:00:00","18:00:00","22:00:00"};
        Calendar   cal   =   Calendar.getInstance();
        String today=new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        cal.add(Calendar.DATE,   1);
        String tomorrow = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        cal.add(Calendar.DATE,   -2);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        System.out.println(yesterday);
        String date[]={yesterday,today,tomorrow};

        int day=0;
        int tim=0;
        if(hour<8){
            tim=1;
        }else if(hour>=8&&hour<12){
            tim=2;
        }else if(hour>=12&&hour<18){
            tim=3;
        }else if(hour>=18&&hour<22){
            tim=4;
        }else if(hour>=22){
            tim=5;
        }

        tim=tim+search;
        if(tim==2){
            day=0;
            tim=3;
        }else if(tim>2&&tim<=6){
            day=1;
            tim=tim-3;
        }else if(tim>6){
            day=2;
            tim=tim-7;
        }

        List<SeckillProduct> list = service.getSeckillProducts(date[day]+time[tim]);
        return list;

    }

    //设置提醒
    @RequestMapping("setRemind/{userId}/{seckillProductId}")
    @ResponseBody
    public void addRemind(@PathVariable int userId,@PathVariable int seckillProductId){
        Remind r =new Remind();
        r.setUser_id(userId);
        r.setSeckillproduct_id(seckillProductId);
        service.addRemind(r);
    }

    //获取秒杀商品
    @RequestMapping("getSeckillProductToSell/{seckillProductId}")
    @ResponseBody
    public SeckillProduct getSeckillProductToSell(@PathVariable int seckillProductId){
        return service.getSeckillProductToSell(seckillProductId);
    }

    //生成秒杀单
    @RequestMapping("addSeckillOrder/{seckillProductId}/{userId}/{addressId}")
    @ResponseBody
    public boolean addSeckillOrder(@PathVariable int seckillProductId,@PathVariable int userId,@PathVariable int addressId){
        Timestamp timestamp = new Timestamp(new Date().getTime());
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setAddress_id(addressId);
        seckillOrder.setSeckillProduct_id(seckillProductId);
        seckillOrder.setUser_id(userId);
        seckillOrder.setOrder_time(timestamp);
        return service.addSeckillOrder(seckillOrder);
    }

    //获取地址
    @RequestMapping("getAddressForSeckill/{userId}")
    @ResponseBody
    public List<Address> getAddressForSeckill(@PathVariable int userId){
        return service.getAddressForSeckill(userId);
    }

    //判断是否已经售完
    @RequestMapping("judgeSeckillProduct/{seckillProductId}")
    @ResponseBody
    public Boolean judgeSeckillProduct(@PathVariable int seckillProductId){
        return service.judgeSeckillProduct(seckillProductId);
    }
}
