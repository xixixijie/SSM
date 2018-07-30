
package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.bean.SeckillProduct;
import com.ssm.model.dao.ClassifyDAO;
import com.ssm.model.dao.ProductDAO;
import com.ssm.model.dao.SeckillProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillProductService {
    @Autowired
    private SeckillProductDAO dao;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ClassifyDAO classifyDAO;
    //查找所有分类
    public List<Classify>  selectAllClassifies(){
        //调用productDAO
        return classifyDAO.showClassify();

    }
    //查找所有商品通过分类
    public List<Product> selectProducts(int classifyId){

        return dao.getProducts(classifyId);
    }
    //查找商品通过商品id
    public  Product selectProduct(int productId){
        //调用product的dao
        return productDAO.getProduct(productId);
    }
    //增加秒杀商品
    public void addSeckillProduct(SeckillProduct seckillProduct){
        dao.addSeckillProduct(seckillProduct);
    }

    //通过秒杀商品id查找秒杀商品
    public SeckillProduct selectSeckillProduct(int seckillProductId){
        return dao.selectSeckillProduct(seckillProductId);
    }

    //通过时间段和商品名查找秒杀商品
    public List<SeckillProduct> selectSeckillProducts(String fromdate, String todate, String name, int pageNumPro){
        Timestamp fromtime=null;
        Timestamp totime=null;
        int pageSize=8;
        if(fromdate!=null&&!"".equals(fromdate)){
            fromtime=Timestamp.valueOf(fromdate+" 00:00:00");
        }
        if(todate!=null&&!"".equals(todate)){
            totime=Timestamp.valueOf(todate+" 23:59:59.999");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fromtime",fromtime);
        map.put("totime",totime);
        map.put("name",name);
        //Page<SeckillProduct> page= PageHelper.startPage(pageNumPro,pageSize);
        Page<SeckillProduct> page = PageHelper.startPage(pageNumPro,pageSize);
        dao.selectSeckillProducts(map);
        List<SeckillProduct> list=page.getResult();
//        List<SeckillProduct> list=dao.selectSeckillProducts(map);

        //和当前时间比较，如果超过24小时，可以修改，如果不超过，则不能修改
        for(int i=0;i<list.size();i++){
            SeckillProduct s = list.get(i);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if((s.getStart_time().getTime() - now.getTime())/(1000*60*60)>24){
                s.setCanDelete(true);
            }else {
                s.setCanDelete(false);
            }
        }
        return list;


    }

    //更改秒杀商品
    public void editSeckillProduct(SeckillProduct seckillProduct){
        dao.editSeckillProduct(seckillProduct);
    }

    //删除秒杀商品
    public void deleteSeckillProduct(int seckillproductId){
        dao.deleteSeckillProduct(seckillproductId);
    }

    //查找页数
    public int selectPageCount(String fromdate, String todate, String name) {
        int pageNum=8;//设置每页多杀条数据
        Timestamp fromtime=null;
        Timestamp totime=null;
        if(fromdate!=null&&!"".equals(fromdate)){
            fromtime=Timestamp.valueOf(fromdate+" 00:00:00");
        }
        if(todate!=null&&!"".equals(todate)){
            totime=Timestamp.valueOf(todate+" 23:59:59.999");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fromtime",fromtime);
        map.put("totime",totime);
        map.put("name",name);

        int count=dao.selectPageCount(map);
        int pageCount=0;
        if(count%pageNum==0){
            pageCount=count/pageNum;
        }else {
            pageCount=count/pageNum+1;
        }
        return pageCount;
    }
}