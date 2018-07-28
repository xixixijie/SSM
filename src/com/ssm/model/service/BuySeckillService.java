package com.ssm.model.service;

import com.ssm.model.bean.Remind;
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

    public void addRemind(Remind r) {
        dao.addRemind(r);
    }
}
