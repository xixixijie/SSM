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

    public List<Classify> getAllClassify();
    public void addProduct(Map<String, Object> map);
    public int getCurrentProductId();
    public void addAspect(Map<String, Object> map);
    public void addParameter(Map<String, Object> map);
    public List<Product> findAllProduct();
    public Product getProductById(int product_id);
    public int getAllPageCount();
    public void updateProduct(Product product);
    public List<Product> getProductByName(String search_info);
    public int getPageCountByName(String search_info);
    public List<String> getFullName(String search_info);
    public void deleteProduct(int[] ids);
    public List<String> getAspectForUser(int product_id);
    public Product getProductForUser(int product_id);
    public List<String> getParameterForUser(int product_id);
    public List<Product> getNewPhone();
    public List<Product> getNewTV();
    public List<Product> getNewPC();
    public List<Product> getNewElec();
    public int checkProDelete(int product_id);
}
