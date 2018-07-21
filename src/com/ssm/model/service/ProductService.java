package com.ssm.model.service;

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
            plist.add(productDAO.getProduct(Integer.parseInt(pid)));
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
