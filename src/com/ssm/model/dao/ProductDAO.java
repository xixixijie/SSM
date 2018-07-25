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



    public List<Classify> getAllClassify();
    public void addProduct(Map<String, Object> map);
    public int getCurrentProductId();
    public void addAspect(Map<String, Object> map);
    public void addParameter(Map<String, Object> map);
    public List<Product> findAllProduct();
    public Product getProductById(int product_id);
    public int getAllPageCount();

    List<Integer> getBought(int userid);
}
