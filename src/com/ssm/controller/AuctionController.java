package com.ssm.controller;

import com.ssm.model.bean.*;
import com.ssm.model.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */

/**
 * 拍卖品相关控制器
 */
@Controller
public class AuctionController {

    @Autowired
    private AuctionService service;

    /**
     * 添加竞拍物品
     * @param auction
     * @param cover
     * @param files
     * @return
     */

    @RequestMapping(value = "addAuction", method = RequestMethod.POST)
    public String addAuction(Auction auction, @RequestParam MultipartFile cover, @RequestParam("files") MultipartFile[] files) {
        System.out.println("添加竞拍品Controller");
        //下载路径
        String download = "/Users/xixi/Desktop/SSM/WebContent/img";
        //处理封面文件
        //旧文件名
        String oldCoverName = cover.getOriginalFilename();
        //新文件名
        String newCoverName = System.currentTimeMillis() + oldCoverName.substring(oldCoverName.indexOf("."));
        //封面文件
        File coverfile = new File(download, newCoverName);
        try {
            cover.transferTo(coverfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //处理介绍图片
        System.out.println(files.length);
        //旧文件名字
        String oldName="";
        //新文件名字
        String newName="";
        //介绍图名字数组
        String[] pics=new String[files.length];
        int i=0;
        for(MultipartFile multipartFile:files){
            oldName=multipartFile.getOriginalFilename();
            newName=System.currentTimeMillis() + oldCoverName.substring(oldCoverName.indexOf("."));

            File pic=new File(download,newName);
            try {
                multipartFile.transferTo(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pics[i++]=newName;
        }

        auction.setCover_url(newCoverName);
        //填充auction
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        //拍卖开始时间
        Date begindate = null;
        //拍卖结束时间
        Date enddate = null;
        String strDate1 = auction.getBegin() + " 00:00:00";
        String strDate2 = auction.getEnd() + " 00:00:00";

        try {
            begindate = sf.parse(strDate1);
            enddate = sf.parse(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        auction.setState(1);
        auction.setWanted(0);
        auction.setHighest_price(0);
        auction.setVisited(0);
        auction.setBeginDate(new Timestamp(begindate.getTime()));
        auction.setEndDate(new Timestamp(enddate.getTime()));

        service.addAuction(auction, pics);


        return "addAuctionPage";


    }

    /**
     * 获得所有竞拍品
     * @param pagenum
     * @return
     */
    @RequestMapping(value = "getAuctions/{pagenum}")
    @ResponseBody
    public List<Auction> getAuctions(@PathVariable int pagenum) {

        System.out.println("-----获得所有拍卖品Controller-----");
        List<Auction> list = service.getAuctions(pagenum, 1);
        for (Auction a : list) {
            a.change();
        }
        return list;
    }

    /**
     * 根据id获得竞拍品
     * @param aid
     * @return
     */
    @RequestMapping(value = "getAuction/{aid}")
    @ResponseBody
    public Auction getAuction(@PathVariable int aid) {
        System.out.println("-----获得拍卖品Controller-----");
        Auction auction = service.getAuction(aid);
        auction.change();
        return auction;
    }

    /**
     * 根据拍卖品id 获得介绍图
     * @param aid
     * @return
     */

    @RequestMapping(value = "getAuctionPic/{aid}")
    @ResponseBody
    public List<AuctionPic> getAuctionPic(@PathVariable int aid) {
        System.out.println("-----获得轮播图Controller-----");
        return service.getAuctionPic(aid);
    }

    /**
     * 根据拍卖品id获得竞拍历史
     * @param aid
     * @return
     */
    @RequestMapping(value = "getHistory/{aid}")
    @ResponseBody
    public List<History> getHistory(@PathVariable int aid) {
        System.out.println("-----获得历史Controller-----");
        return service.getHistory(aid);

    }

    /**
     * 添加一条竞拍历史
     * @param userid
     * @param aid
     * @param price
     * @param wanted
     */
    @RequestMapping(value = "addHistory/{userid}/{aid}/{price}/{wanted}")
    @ResponseBody
    public void addHistory(@PathVariable int userid, @PathVariable int aid, @PathVariable double price, @PathVariable int wanted) {
        System.out.println("-----添加历史Controller-----");
        service.addHistory(userid, aid, price, wanted);
    }

    /**
     * 获得拍卖订单
     * @return
     */
    @RequestMapping(value = "getAuctionOrder/{userID}")
    @ResponseBody
    public List<AuctionOrder> getAuctionOrder(@PathVariable int userID) {
        System.out.println("-----获取拍卖订单Controller-----");
        List<AuctionOrder> list = service.getAuctionOrder(userID);
        for (AuctionOrder ao : list) {
            ao.change();
        }
        return list;
    }
}
