package com.ssm.model.service;

import com.ssm.model.bean.Address;
import com.ssm.model.bean.Remind;
import com.ssm.model.bean.SeckillOrder;
import com.ssm.model.bean.SeckillProduct;
import com.ssm.model.dao.BuySeckillDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuySeckillService {

    @Autowired
    private BuySeckillDAO dao;

    //加载秒杀商品
    public List<SeckillProduct> getSeckillProducts(String s) {
        Timestamp t = Timestamp.valueOf(s);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("t",t);
        List<SeckillProduct> list = dao.getSeckillProducts(map);
        for(int i=0;i<list.size();i++){
            SeckillProduct seckillProduct=list.get(i);
            //查找秒杀单
            int amount = dao.getCount(seckillProduct.getSeckillproduct_id());
            if(amount==seckillProduct.getAll_amount()){
                seckillProduct.setCanBuy(false);
            }else {
                seckillProduct.setCanBuy(true);
            }
        }
        return list;
    }

    //增加提醒
    public void addRemind(Remind r) {
        if(dao.getRemindAmount(r).size()==0){
            dao.addRemind(r);
        }

    }

    //获得秒杀商品
    public SeckillProduct getSeckillProductToSell(int seckillProductId) {
        return dao.getSeckillProductToSell(seckillProductId);
    }

    public boolean addSeckillOrder(SeckillOrder seckillOrder) {
        //先判断是否商品售完
        SeckillProduct seckillProduct = dao.getSeckillProductToSell(seckillOrder.getSeckillProduct_id());
        int count = dao.getCount(seckillOrder.getSeckillProduct_id());
        if(count>=seckillProduct.getAll_amount()){
            return false;
        }

        //添加
        dao.addSeckillOrder(seckillOrder);
        return true;
    }

    public List<Address> getAddressForSeckill(int userId) {
        return dao.getAddressForSeckill(userId);
    }

    //判断商品是否已经售完
    public Boolean judgeSeckillProduct(int seckillProductId) {
        int allAmount=dao.getSeckillProductToSell(seckillProductId).getAll_amount();
        //System.out.println("总秒杀数为："+allAmount);
        int nowAmount=dao.getCount(seckillProductId);
        //System.out.println("当前秒杀单数量为："+nowAmount);
        if(nowAmount>=allAmount){
            return false;
        }else {
            return true;
        }
    }
}
