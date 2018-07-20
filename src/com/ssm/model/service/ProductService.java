package com.ssm.model.service;

import com.ssm.model.bean.Product;
import com.ssm.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public List<Product> getProducts(int classifyID){
        System.out.println("-----获取分类商品service-----");
        List<Product> list=productDAO.getProducts(classifyID);
        if(list==null||list.size()==0){
            System.out.println("-----获得商品种类"+classifyID+"失败-----");
        }else{
            System.out.println("-----获得商品种类"+classifyID+"大小为"+list.size()+"-----");
        }

        return list;
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
