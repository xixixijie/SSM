package com.ssm.model.dao;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by xixi on 2018/7/19.
 */
public interface ProductDAO {
    public List<Product> getProducts(int classifyID);
    public List<Product> getAllProduct();

    public Product getProduct(int pid);

    List<Integer> getBought(int userid);

    //范东升部分 begin
    public List<Classify> getAllClassify();    //获得所有分类
    public void addProduct(Map<String, Object> map);   //添加商品信息
    public int getCurrentProductId();     //获得当前添加商品的自增id
    public void addAspect(Map<String, Object> map);    //添加商品的外观轮播图
    public void addParameter(Map<String, Object> map);    //添加商品的参数图
    public List<Product> findAllProduct();      //获取所有商品
    public Product getProductById(int product_id);     //根据id获取商品
    public int getAllPageCount();       //获取总页数
    public void updateProduct(Product product);     //更新商品
    public List<Product> getProductByName(String search_info);  //根据关键字获取商品列表并分页
    public int getPageCountByName(String search_info);  //根据商品关键字获取商品列表的总页数
    public List<String> getFullName(String search_info);    //智能补全商品名
    public void deleteProduct(int[] ids);   //删除商品
    public List<String> getAspectForUser(int product_id);   //根据id获得单个商品的外观轮播图
    public Product getProductForUser(int product_id);       //根据id获得单个商品的信息
    public List<String> getParameterForUser(int product_id);    //根据id获得单个商品的参数图
    public List<Product> getNewPhone();     //按上市日期倒序获得最新手机商品列表
    public List<Product> getNewTV();    //按上市日期倒序获得最新电视商品列表
    public List<Product> getNewPC();    //按上市日期倒序获得最新笔记本商品列表
    public List<Product> getNewElec();  //按上市日期倒序获得最新智能家居商品列表
    public int checkProDelete(int product_id);  //检验商品是否可以删除
    public int checkProName(String product_name);   //检验商品名是否重复
    //范东升部分 end
}
