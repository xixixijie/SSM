package com.ssm.model.service;

import com.ssm.model.bean.Product;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public class ProductService {
    public List<Product> getProducts(int classifyID){
        System.out.println("-----获取分类商品service-----");

        return null;
    }

    public void storeRecord(Product product){
        System.out.println("-----存记录service-----");

    }

    public List<Product> getRecord(){
        System.out.println("-----获得记录service-----");

        return null;
    }

    public List<Product> introProduct(){
        System.out.println("-----推荐商品service-----");

        return null;
    }

    private List<Product> chooseProduct(List<Product> list){
        System.out.println("-----选择商品service-----");

        return null;
    }
}
