
package com.ssm.model.dao;


import com.ssm.model.bean.Product;
import com.ssm.model.bean.SeckillProduct;

import java.util.List;
import java.util.Map;


public interface SeckillProductDAO {

    //添加秒杀商品
    void addSeckillProduct(SeckillProduct seckillProduct);

    //通过秒杀商品id查找秒杀商品
    SeckillProduct selectSeckillProduct(int seckillProductId);

    //修改秒杀商品
    void editSeckillProduct(SeckillProduct seckillProduct);

    //删除秒杀商品通过秒杀id
    void deleteSeckillProduct(int seckillProductId);

    //通过插叙条件查找符合条件的秒杀商品
    List<SeckillProduct> selectSeckillProducts(Map<String,Object> map);

    //通过分类id查找所有商品
    List<Product> getProducts(int classifyId);

    //查找页数
    int selectPageCount(Map<String,Object> map);
}