package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.bean.UserInfo;
import com.ssm.model.dao.CommentDAO;
import com.ssm.model.dao.ProductDAO;
import com.ssm.model.dao.UserInfoDAO;
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

    @Autowired
    private UserInfoDAO userInfoDAO;


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

    public void updateProduct(Product product){
        productDAO.updateProduct(product);
    }

    public List<Product> getProductByName(String search_info,int pageNum){
        int pageSize=8;
        List<Product> list=new ArrayList<Product>();
        Page<Product> page= PageHelper.startPage(pageNum,pageSize);
        productDAO.getProductByName(search_info);
        list=page.getResult();
        return list;
    }

    public int getPageCountByName(String search_info){
        int count=0;
        int pageSize=8;
        int pagecount = 0;
        count=productDAO.getPageCountByName(search_info);
        if(count%pageSize==0){
            pagecount = count/pageSize;
        }else{
            pagecount = count/pageSize+1;
        }
        return pagecount;
    }

    public List<String> getFullName(String search_info){
        List<String> nameList=new ArrayList<String>();
        nameList=productDAO.getFullName(search_info);
        return nameList;
    }

    public void deleteProduct(int[] ids){
        productDAO.deleteProduct(ids);
    }

    public List<Product> getProductForUserByName(String search_info){
        List<Product> list=new ArrayList<Product>();
        list=productDAO.getProductByName(search_info);
        return list;
    }

    public List<String> getAspectForUser(int product_id){
        List<String> aspectList=new ArrayList<String>();
        aspectList=productDAO.getAspectForUser(product_id);
        return aspectList;
    }

    public Product getProductForUser(int product_id){
        Product product=new Product();
        product=productDAO.getProductForUser(product_id);
        return product;
    }

    public List<String> getParameterForUser(int product_id){
        List<String> parameterList=new ArrayList<String>();
        parameterList=productDAO.getParameterForUser(product_id);
        return parameterList;
    }

    public List<Product> getNewPhone(){
        List<Product> phoneList=new ArrayList<Product>();
        phoneList=productDAO.getNewPhone();
        return phoneList;
    }

    public List<Product> getNewTV(){
        List<Product> tvList=new ArrayList<Product>();
        tvList=productDAO.getNewTV();
        return tvList;
    }

    public List<Product> getNewPC(){
        List<Product> pcList=new ArrayList<Product>();
        pcList=productDAO.getNewPC();
        return pcList;
    }

    public List<Product> getNewElec(){
        List<Product> elecList=new ArrayList<Product>();
        elecList=productDAO.getNewElec();
        return elecList;
    }

    public boolean checkProDelete(int product_id){
        int count=productDAO.checkProDelete(product_id);
        return count>0?false:true;
    }

    public boolean checkProName(String product_name){
        int count=productDAO.checkProName(product_name);
        return count>0?true:false;
    }

    //范东升end

    public List<Product> getProducts(int classifyID) {
        System.out.println("-----获取分类商品service-----");
        //System.out.println(classifyID);
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
        List<String> pids=new ArrayList<>();
        for(String pid:strs){
            if(pid.equals("undefined"))
                continue;
            if(pids.contains(pid))
                continue;
            pids.add(pid);
            Product p=productDAO.getProduct(Integer.parseInt(pid));
            p.setCommentNum(commentDAO.getCommentNum(Integer.parseInt(pid)));
            plist.add(p);

        }
        return plist;
    }

    //基于用户的协同过滤推荐算法及商品热度排序混合推荐
    public List<Product> introProduct(int userid) {
        System.out.println("-----推荐商品service-----");
        //获取所有商品
        List<Product> list = productDAO.getAllProduct();
        //热度排序选了9个
        List<Product> list1=chooseProduct(list);
        //基于用户的协同过滤算法
        List<Product> list2=chooseProduct(userid,list);
        //list1.addAll(list2);
        return list1;
    }


    private List<Product> chooseProduct(int userid,List<Product> list) {
        //获取用户
        //System.out.println(userid);
        List<UserInfo> userlist=userInfoDAO.selectUsers();
        //System.out.println("用户大小"+userlist.size());

        //用户与物品的关系
        Map<Integer,List<Integer>> User2Product_map=new HashMap<>();

        for(UserInfo u:userlist){
            //根据用户id 获得用户买过的商品id
            User2Product_map.put(u.getUser_id(),getBought(u.getUser_id()));
        }
        //物品与用户的关系
        Map<Integer,List<Integer>> Product2User_map=new HashMap<>();
        //时间复杂度 O（nm） n用户数 m商品数
        for(Product p:list){
            //买过该商品的客户 userids
            List<Integer> userids=new ArrayList<>();
            for(Integer key:User2Product_map.keySet()){
                if(User2Product_map.get(key).contains(p.getProduct_id())){
                    userids.add(key);
                }
            }
            Product2User_map.put(p.getProduct_id(),userids);
        }

        //创建相似数组
        int userSize=userlist.size();
        double[] array=new double[userSize];
        //填写相似数组

        //遍历所有商品
        for(Integer key:Product2User_map.keySet()){
            //得到买过这商品的所有用户
            List<Integer> users=Product2User_map.get(key);

            //如果该商品被当前用户买过
            if(users.contains(userid)){
                //两两+1
                for(int i=0;i<users.size();i++){
                    //得到非当前用户的id
                    int u1=users.get(i);
                    //如果不是当前用户
                    if(u1!=userid){
                        //找出非当前用户在所有用户list中的index
                        for(int j=0;j<userSize;j++){
                            if(userlist.get(j).getUser_id()==u1){
                                array[j]++;
                            }
                        }
                    }
                }

            }




        }


        //计算相似度
        for(int j=0;j<userSize;j++){

            array[j]=calcuSimilarity(userid,j,User2Product_map,userlist);
        }

        //找出和userid相似度最高的用户
        int point=0;
        double max=array[0];
        for(int i=1;i<userSize;i++){
            if(array[i]>max){
                point=i;
                max=array[i];

            }
        }

        List<Integer> pids1=User2Product_map.get(userid);
        List<Integer> pids2=User2Product_map.get(userlist.get(point).getUser_id());
        List<Product> newList=new ArrayList<>();
        //把用户2买过而当前用户没买过的商品推荐给当前用户
        for(Integer pid:pids2){
            if(!pids1.contains(pid)){
                //遍历所有商品
                for(Product p:list){
                    if(p.getProduct_id()==pid){
                        newList.add(p);
                    }
                }
            }
        }



        return newList;
    }

    private double calcuSimilarity(int userid,int j, Map<Integer, List<Integer>> user2Product_map, List<UserInfo> userlist) {
        //根据index查对应用户

        int u1=userlist.get(j).getUser_id();
        //根据用户得到用户买过的商品
        List<Integer> p1=user2Product_map.get(userid);
        List<Integer> p2=user2Product_map.get(u1);

        //计算相交 N（u1）交 N（u2）
        int count=0;
        for(int m=0;m<p1.size();m++){
            for(int n=0;n<p2.size();n++){
                if(p1.get(m)==p2.get(n)){
                    count++;
                }
            }
        }

        return count/Math.sqrt(p1.size()*p2.size());


    }


    //根据用户id 获得用户买过的商品id
    public List<Integer> getBought(int userid){
        List<Integer> list=productDAO.getBought(userid);
        //System.out.println(userid+"买过的商品大小"+list.size());
        return list;
    }

    private List<Product> chooseProduct(List<Product> list) {
        System.out.println("-----推荐商品方法-----");
        //计算每个商品的热度
        List<Hot> hotList = caculateHot(list);
        //根据热度排序，选最高的6个
        Collections.sort(hotList);
        List<Product> newList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            //System.out.println(hotList.get(i).getScore()+" "+hotList.get(i).getProduct().getProduct_name());
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

        
        return hour * 0.2 + count * 0.8;
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
