package com.ssm.controller;

import com.ssm.model.bean.Product;
import com.ssm.model.dao.ProductDAO;
import com.ssm.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getProductsByClassifyID/{classifyID}")
    @ResponseBody
    public List<Product> getProducts(@PathVariable int classifyID){
        System.out.println("-----分类查询Controller-----");
//        List<Product> list=productService.getProducts(classifyID);
        return productService.getProducts(classifyID);
    }

    public void storeRecord(Product product){
        System.out.println("-----存记录Controller-----");


    }

    public List<Product> getRecord(){
        System.out.println("-----获得记录Controller-----");

        return null;
    }

    @RequestMapping(value = "introProduct")
    @ResponseBody
    public List<Product> introProduct(){
        System.out.println("-----推荐商品Controller-----");
        return productService.introProduct();
    }
}
