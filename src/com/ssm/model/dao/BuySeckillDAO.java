package com.ssm.model.dao;

import com.ssm.model.bean.Address;
import com.ssm.model.bean.Remind;
import com.ssm.model.bean.SeckillOrder;
import com.ssm.model.bean.SeckillProduct;

import java.sql.Timestamp;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public interface BuySeckillDAO {

    //加载秒杀商品
    List<SeckillProduct> getSeckillProducts(Map<String, Object> map);

    //查找秒杀单
    int getCount(int seckillproduct_id);

    //添加提醒
    void addRemind(Remind r);

    //获取秒杀商品
    SeckillProduct getSeckillProductToSell(int seckillProductId);

    //添加秒杀单
    void addSeckillOrder(SeckillOrder seckillOrder);

    //获得地址
    List<Address> getAddressForSeckill(int userId);

    //获取是否已经有提醒
    List<Remind> getRemindAmount(Remind r);
}
