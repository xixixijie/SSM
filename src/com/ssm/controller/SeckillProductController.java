package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.bean.SeckillProduct;
import com.ssm.model.service.SeckillProductService;
import com.ssm.Task.RemindTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class SeckillProductController {
    @Autowired
    private SeckillProductService service;



    //查找所有的分类，返回所有分类
    @RequestMapping("selectClassifies")
    public @ResponseBody List<Classify> selectClassifies(){
        List<Classify> classifies= service.selectAllClassifies();

        return classifies;
    }

    //根据分类id找到所有分类下的商品
    @RequestMapping("selectProductsByClassifyId")
    public @ResponseBody List<Product> selectProductsByClassifyId(String classifyId){

        System.out.println(classifyId);
        List<Product> l=service.selectProducts(Integer.parseInt(classifyId));
        for(Product p:l){
            System.out.println(p.getProduct_name()+"  "+p.getOriginal_price());
        }
        return l;

    }

    //通过商品id查找商品
    @RequestMapping("setSeckillPrice")
    public @ResponseBody Product setSeckillPrice(String productId){
        System.out.println("进入查询单个商品服务器");
        return service.selectProduct(Integer.parseInt(productId));
    }

    //增加秒杀商品
    @RequestMapping("addSeckillProduct")
    public String addSeckillProduct(int productId,String date,String time,int allamount,double seckillprice){
        Timestamp t=Timestamp.valueOf(date+" "+time);
        SeckillProduct s=new SeckillProduct();
        s.setAll_amount(allamount);
        s.setProduct_id(productId);
        s.setStart_time(t);
        s.setSeckill_price(seckillprice);
        service.addSeckillProduct(s);
        return "AddSeckillProduct";
    }

    //查找所有秒杀商品通过数据
    @RequestMapping("selectSeckillProducts")
    public String selectSeckillProducts(String startdate, String enddate, String name, Model model, HttpSession session){
        int pageCount=0;
        int pageNumPro=1;
        System.out.println(startdate);
        System.out.println(enddate);
        System.out.println(name);
        //页数
        pageCount = service.selectPageCount(startdate,enddate,name);
        //商品列表
        List<SeckillProduct> list=service.selectSeckillProducts(startdate,enddate,name,pageNumPro);
        for(SeckillProduct s:list){
            System.out.println(s.getSeckill_price());
            System.out.println(s.getProduct().getProduct_name());
        }

        model.addAttribute("list",list);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("pageNumPro",pageNumPro);
        session.setAttribute("startdate",startdate);
        session.setAttribute("enddate",enddate);
        session.setAttribute("name",name);
        return "SelectSeckillProduct";
    }

    //获取要更改的秒杀商品
    @RequestMapping("updateSeckill")
    public @ResponseBody SeckillProduct updateSeckill(int seckillId){
        return service.selectSeckillProduct(seckillId);
    }

    //通过页数查询
    @RequestMapping("selectSeckillProductsByPage")
    public String SelectSeckillByPage(int pageNumPro,HttpSession session,Model model){
        String startdate = (String) session.getAttribute("startdate");
        String enddate = (String) session.getAttribute("enddate");
        String name = (String) session.getAttribute("name");
        int pageCount=0;
        System.out.println(startdate);
        System.out.println(enddate);
        System.out.println(name);
        //页数
        pageCount = service.selectPageCount(startdate,enddate,name);
        //商品列表
        List<SeckillProduct> list=service.selectSeckillProducts(startdate,enddate,name,pageNumPro);
        for(SeckillProduct s:list){
            System.out.println(s.getSeckill_price());
            System.out.println(s.getProduct().getProduct_name());
        }

        model.addAttribute("list",list);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("pageNumPro",pageNumPro);
        session.setAttribute("startdate",startdate);
        session.setAttribute("enddate",enddate);
        session.setAttribute("name",name);
        return "SelectSeckillProduct";
    }

    //更改秒杀商品
    @RequestMapping("editSeckillProduct")
    public String editSeckillProduct(int seckillProductId,double seckillPrice,int allAmount,HttpSession session){
        SeckillProduct s = new SeckillProduct();
        s.setSeckill_price(seckillPrice);
        s.setSeckillproduct_id(seckillProductId);
        s.setAll_amount(allAmount);
        service.editSeckillProduct(s);
        int pageNumPro = (int) session.getAttribute("pageNumPro");
        return "redirect:selectSeckillProductsByPage.action?pageNumPro="+pageNumPro;

    }

    //删除秒杀商品
    @RequestMapping("deleteSeckillProduct")
    public String deleteSeckillProduct(int seckillProductId,HttpSession session){
        service.deleteSeckillProduct(seckillProductId);
        int pageNumPro = (int) session.getAttribute("pageNumPro");
        return "redirect:selectSeckillProductsByPage.action?pageNumPro="+pageNumPro;
    }
}
