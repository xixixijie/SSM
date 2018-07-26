package com.ssm.controller;

import com.ssm.model.bean.Auction;
import com.ssm.model.bean.AuctionPic;
import com.ssm.model.bean.History;
import com.ssm.model.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
@Controller
public class AuctionController {

    @Autowired
    private AuctionService service;

    @RequestMapping(value = "getAuctions")
    @ResponseBody
    public List<Auction> getAuctions(){

        System.out.println("-----获得所有拍卖品Controller-----");
        List<Auction> list=service.getAuctions();
        for(Auction a:list){
            a.change();
        }
        return list;
    }

    @RequestMapping(value = "getAuction/{aid}")
    @ResponseBody
    public Auction getAuction(@PathVariable int aid){
        System.out.println("-----获得拍卖品Controller-----");
        return service.gAuction(aid);
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

    @RequestMapping(value = "addHistory/{useerid}/{aid}/{price}")
    @ResponseBody
    public void addHistory(@PathVariable int userid,@PathVariable int aid,@PathVariable double price){
        System.out.println("-----添加历史Controller-----");
        service.addHistory(userid,aid,price);
    }
}
