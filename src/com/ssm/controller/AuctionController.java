package com.ssm.controller;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionOrder;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;
import com.ssm.model.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
@Controller
public class AuctionController {

    @Autowired
    private AuctionService service;

    @RequestMapping(value = "getAuctions/{pagenum}")
    @ResponseBody
    public List<Auction> getAuctions(@PathVariable int pagenum){

        System.out.println("-----获得所有拍卖品Controller-----");
        List<Auction> list=service.getAuctions(pagenum, 1);
        for(Auction a:list){
            a.change();
        }
        return list;
    }

    @RequestMapping(value = "getAuction/{aid}")
    @ResponseBody
    public Auction getAuction(@PathVariable int aid){
        System.out.println("-----获得拍卖品Controller-----");
        Auction auction=service.gAuction(aid);
        auction.change();
        return auction;
    }

    @RequestMapping(value = "getAuctionPic/{aid}")
    @ResponseBody
    public List<AuctionPic> getAuctionPic(@PathVariable int aid){
        System.out.println("-----获得轮播图Controller-----");
        return service.gAuctionPic(aid);
    }

    @RequestMapping(value = "getHistory/{aid}")
    @ResponseBody
    public List<History> getHistory(@PathVariable int aid){
        System.out.println("-----获得历史Controller-----");
        return service.gHistory(aid);

    }

    @RequestMapping(value = "addHistory/{userid}/{aid}/{price}/{wanted}")
    @ResponseBody
    public void addHistory(@PathVariable int userid,@PathVariable int aid,@PathVariable double price,@PathVariable int wanted){
        System.out.println("-----添加历史Controller-----");
        service.addHistory(userid,aid,price,wanted);
    }

    @RequestMapping(value = "getAuctionOrder")
    @ResponseBody
    public List<AuctionOrder> getAuctionOrder(){
        System.out.println("-----获取拍卖订单Controller-----");
        List<AuctionOrder> list=service.getAuctionOrder();
        for(AuctionOrder ao:list){
            ao.change();
        }
        return list;
    }
}
