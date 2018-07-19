package com.ssm.controller;

import com.ssm.model.bean.Product;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class ProductController {
    public List<Product> getProducts(int classifyID){
        System.out.println("-----分类查询Controller-----");

        return null;
    }

    public void storeRecord(Product product){
        System.out.println("-----存记录Controller-----");


    }

    public List<Product> getRecord(){
        System.out.println("-----获得记录Controller-----");

        return null;
    }

    public List<Product> introProduct(){
        System.out.println("-----推荐商品Controller-----");


        return null;
    }
}
