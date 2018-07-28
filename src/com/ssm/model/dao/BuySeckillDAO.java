package com.ssm.model.dao;

import com.ssm.model.bean.Remind;
import com.ssm.model.bean.SeckillProduct;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface BuySeckillDAO {

    //加载秒杀商品
    List<SeckillProduct> getSeckillProducts(Map<String, Object> map);

    //查找秒杀单
    int getCount(int seckillproduct_id);

    //添加提醒
    void addRemind(Remind r);
}
