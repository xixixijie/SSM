package com.ssm.model.dao;

import com.ssm.model.bean.Product;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface ProductDAO {
    public List<Product> getProducts(int classifyID);
    public List<Product> getAllProduct();

    public Product getProduct(int pid);
}
