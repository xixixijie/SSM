package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.dao.CommentDAO;
import com.ssm.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CommentDAO commentDAO;

    //范东升
    public List<Classify> getAllClassify(){
        List<Classify> classifyList=new ArrayList<Classify>();
        classifyList=productDAO.getAllClassify();
        return classifyList;
    }

    public void addProduct(Product product){
        int status=1;
        Date on_date=new Date();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("product", product);
        map.put("status", status);
        map.put("on_date", new java.sql.Date(on_date.getTime()));
        productDAO.addProduct(map);
    }

    public int getCurrentProductId(){
        int product_id=productDAO.getCurrentProductId();
        return product_id;
    }


    public void addAspect(String aspect_url,int product_id){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("aspect_url", aspect_url);
        map.put("product_id", product_id);
        productDAO.addAspect(map);
    }

    public void addParameter(String parameter_url,int product_id){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("parameter_url", parameter_url);
        map.put("product_id", product_id);
        productDAO.addParameter(map);
    }

    public List<Product> getAllPageProduct(int pageNum){
        int pageSize=8;
        List<Product> list=new ArrayList<Product>();
        Page<Product> page= PageHelper.startPage(pageNum,pageSize);
        productDAO.findAllProduct();
        list=page.getResult();
//        System.out.println("公司打仨噶萨嘎上的噶");
//        for (Product product:list) {
//            System.out.println(product.getProduct_id()+"  "+product.getProduct_name());
//      }

        return list;
    }

    public int getAllPageCount(){
        int count=0;
        int pageSize=8;
        int pagecount = 0;
        count=productDAO.getAllPageCount();
        if(count%pageSize==0){
            pagecount = count/pageSize;
        }else{
            pagecount = count/pageSize+1;
        }
        return pagecount;
    }

    public Product getProductById(int product_id){
        Product product=new Product();
        product=productDAO.getProductById(product_id);
        return product;
    }

    //范东升end

    public List<Product> getProducts(int classifyID) {
        System.out.println("-----获取分类商品service-----");
        List<Product> list = productDAO.getProducts(classifyID);
        if (list == null || list.size() == 0) {
            System.out.println("-----获得商品种类" + classifyID + "失败-----");
        } else {
            System.out.println("-----获得商品种类" + classifyID + "大小为" + list.size() + "-----");
        }

        return list;
    }

    public void storeRecord(int productID) {
        System.out.println("-----存记录service-----");

        String fileName = "record";
        try {
            //使用这个构造函数时，如果存在kuka.txt文件，
            //则直接往kuka.txt中追加字符串
            FileWriter writer = new FileWriter(fileName, true);
            SimpleDateFormat format = new SimpleDateFormat();
            String time = format.format(new Date());
            writer.write("\n\t" + time);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Product> getRecord(String record) {
        System.out.println("-----获得记录service-----");
        String strs[]=record.split(",");
        List<Product> plist=new ArrayList<>();
        for(String pid:strs){
            Product p=productDAO.getProduct(Integer.parseInt(pid));
            p.setCommentNum(commentDAO.getCommentNum(Integer.parseInt(pid)));
            plist.add(p);

        }
        return plist;
    }


    public List<Product> introProduct() {
        System.out.println("-----推荐商品service-----");
        //获取所有商品
        List<Product> list = productDAO.getAllProduct();


        return chooseProduct(list);
    }


    private List<Product> chooseProduct(List<Product> list) {
        System.out.println("-----推荐商品方法-----");
        //计算每个商品的热度
        List<Hot> hotList = caculateHot(list);
        //根据热度排序，选最高的10个
        Collections.sort(hotList);
        List<Product> newList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            newList.add(hotList.get(i).getProduct());
        }
        return newList;
    }

    private List<Hot> caculateHot(List<Product> plist) {
        List<Hot> l = new ArrayList<>();
        for (Product p : plist) {
            Hot h = new Hot(p);
            l.add(h);
        }
        for (Hot h : l) {
            h.setScore(getProductHot(h.getProduct()));
        }
        return l;
    }

    private Double getProductHot(Product product) {
        //计算推出日期到现在的小时数，转换数量级
        Double hour = Double.valueOf(Math.abs(product.getOn_date().getTime() - new Date().getTime()) / 1000 / 60 / 60);

        //获得和该商品有关的评论数
        int count = commentDAO.getCommentNum(product.getProduct_id());

        return hour * 0.4 + count * 0.6;
    }


    class Hot implements Comparable {
         Hot(Product product) {
            this.product = product;
        }

        private Product product;
        private Double score;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

         Double getScore() {
            return score;
        }

         void setScore(Double score) {
            this.score = score;
        }

        //升序 <
        //降序 >
        @Override
        public int compareTo(Object o) {
            Double x = this.score;
            Double y = ((Hot) o).getScore();
            return (x > y) ? -1 : ((x == y) ? 0 : 1);
        }
    }
}
