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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xixi on 2018/7/24.
 */
@Controller
public class AuctionController {

    @Autowired
    private AuctionService service;


    public String saveFile(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        String path = "/library";
        String newName = originalName.substring(originalName.lastIndexOf("."));
        File file = new File(path + newName);

        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return "/images/" + newName;
    }


    @RequestMapping(value = "addAuction", method = RequestMethod.POST)
    public String addAuction(Auction auction, @RequestParam MultipartFile cover, @RequestParam("file") MultipartFile[] files) {
        System.out.println("添加竞拍品Controller");
        String download = "/Users/xixi/Desktop/SSM/WebContent/img";
        //处理封面文件
        String oldCoverName = cover.getOriginalFilename();
        String newCoverName = System.currentTimeMillis() + oldCoverName.substring(oldCoverName.indexOf("."));

        File coverfile = new File(download, newCoverName);
        try {
            cover.transferTo(coverfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //处理介绍图片
        String oldName="";
        String newName="";
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
        //System.out.println(newCoverName);
        auction.setCover_url(newCoverName);
        //填充auction
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date begindate = null;
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

    @RequestMapping(value = "getAuction/{aid}")
    @ResponseBody
    public Auction getAuction(@PathVariable int aid) {
        System.out.println("-----获得拍卖品Controller-----");
        Auction auction = service.gAuction(aid);
        auction.change();
        return auction;
    }

    @RequestMapping(value = "getAuctionPic/{aid}")
    @ResponseBody
    public List<AuctionPic> getAuctionPic(@PathVariable int aid) {
        System.out.println("-----获得轮播图Controller-----");
        return service.gAuctionPic(aid);
    }

    @RequestMapping(value = "getHistory/{aid}")
    @ResponseBody
    public List<History> getHistory(@PathVariable int aid) {
        System.out.println("-----获得历史Controller-----");
        return service.gHistory(aid);

    }

    @RequestMapping(value = "addHistory/{userid}/{aid}/{price}/{wanted}")
    @ResponseBody
    public void addHistory(@PathVariable int userid, @PathVariable int aid, @PathVariable double price, @PathVariable int wanted) {
        System.out.println("-----添加历史Controller-----");
        service.addHistory(userid, aid, price, wanted);
    }

    @RequestMapping(value = "getAuctionOrder")
    @ResponseBody
    public List<AuctionOrder> getAuctionOrder() {
        System.out.println("-----获取拍卖订单Controller-----");
        List<AuctionOrder> list = service.getAuctionOrder();
        for (AuctionOrder ao : list) {
            ao.change();
        }
        return list;
    }
}
