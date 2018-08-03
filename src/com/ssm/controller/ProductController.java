package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 * 商品管理模块的Controller
 */
@Controller
public class ProductController {

    @Autowired      //注入service
    private ProductService productService;

    //范东升部分 begin

    //获取所有分类列表并跳转至添加商品页面
    @RequestMapping("goToAddProduct")
    public String goToAddProduct(Model model){
        List<Classify> classifyList=new ArrayList<Classify>();
        classifyList=productService.getAllClassify();

        model.addAttribute("classifyList",classifyList);
        return "addProduct";
    }

    //添加商品
    @RequestMapping(value="addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product,@RequestParam MultipartFile cover, @RequestParam MultipartFile[] aspectPics, @RequestParam MultipartFile[] parameterPics){

        String oldAspectName="";
        String oldParameterName="";
        String oldCoverName="";

        String newAspectName="";
        String newParameterName="";
        String newCoverName="";

        String aspectUrl="D:\\javaWeb/MI2/WebContent/img";
        String parameterUrl="D:\\javaWeb/MI2/WebContent/img";
        String coverUrl="D:\\javaWeb/MI2/WebContent/img";

        oldCoverName=cover.getOriginalFilename();
        newCoverName=System.currentTimeMillis()+oldCoverName.substring(oldCoverName.indexOf("."));
        File coverfile = new File(coverUrl,newCoverName);
        try {
            cover.transferTo(coverfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setCover_url(newCoverName);

        productService.addProduct(product);
        //获得刚刚插入的商品的id
        int product_id=productService.getCurrentProductId();

        for (int i = 0; i < aspectPics.length; i++) {
            oldAspectName = aspectPics[i].getOriginalFilename();
            newAspectName = System.currentTimeMillis()+oldAspectName.substring(oldAspectName.indexOf("."));

            File file = new File(aspectUrl,newAspectName);
            try {
                aspectPics[i].transferTo(file);
                productService.addAspect(newAspectName,product_id);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < parameterPics.length; i++) {
            oldParameterName = parameterPics[i].getOriginalFilename();
            newParameterName = System.currentTimeMillis()+oldParameterName.substring(oldParameterName.indexOf("."));

            File file = new File(parameterUrl,newParameterName);
            try {
                parameterPics[i].transferTo(file);
                productService.addParameter(newParameterName,product_id);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:goToAddProduct.action";
    }

    //进入搜索商品页面
    @RequestMapping("goToSearchProduct")
    public String goToSearchProduct(Model model, int pageNumPro, HttpSession session){
        int pageCount=0;

        List<Product> productList=new ArrayList<Product>();
        productList=productService.getAllPageProduct(pageNumPro);
        for (Product pro:productList) {
            int pro_id = pro.getProduct_id();
            boolean isDelete=productService.checkProDelete(pro_id);
            System.out.println(pro_id+"  "+isDelete);
            pro.setDelete(isDelete);
        }
        pageCount=productService.getAllPageCount();
        model.addAttribute("productList",productList);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("pageNumPro",pageNumPro);
        return "searchProduct";
    }

    //查看商品详细信息
    @RequestMapping("getProductInfo")
    public String getProductInfo(int product_id,Model model){
        Product product=new Product();
        product=productService.getProductById(product_id);
        model.addAttribute("product",product);
        System.out.println("进入getProdcutInfo.action");
        return "productInfo";
    }

    //进入编辑商品信息页面
    @RequestMapping("editProduct")
    public String editProduct(Model model,int product_id){
        //获取分类列表
        List<Classify> classifyList=new ArrayList<Classify>();
        classifyList=productService.getAllClassify();
        //根据id获取商品信息
        Product product=new Product();
        product=productService.getProductById(product_id);

        model.addAttribute("classifyList",classifyList);
        model.addAttribute("product",product);
        System.out.println("进入editProduct.action");
        return "editProduct";
    }

    //更新商品信息
    @RequestMapping("updateProduct")
    public String updateProduct(@ModelAttribute Product product, HttpSession session,Model model){
        String pageNumPro=session.getAttribute("pageNumPro").toString();
        System.out.println("进入updateProduct.action");
        System.out.println(pageNumPro);
        int product_id=product.getProduct_id();
        System.out.println(product.getProduct_id()+"  "+product.getProduct_name()+"  "
                +product.getClassify().getClassifyID()+"  "+product.getOriginal_price()+"  "+
                product.getDiscount_price()+"  "+product.getProduct_info());
        productService.updateProduct(product);
        return "redirect:goToSearchProduct.action?pageNumPro="+pageNumPro;
    }

    //根据关键字查询相关商品
    @RequestMapping("getProductByName")
    public String getProductByName(String search_info,int pageNumPro,Model model,HttpSession session){
        int pageCount=0;
        List<Product> productList=new ArrayList<Product>();
        //如果搜索内容为空，则查询所有商品；如果搜索内容不为空，则搜索包含该内容关键字的商品
        if (search_info != null && !"".equals(search_info)){
            productList=productService.getProductByName(search_info,pageNumPro);
            pageCount=productService.getPageCountByName(search_info);
        }else {
            productList=productService.getAllPageProduct(pageNumPro);
            pageCount=productService.getAllPageCount();
        }
        for (Product pro:productList) {
            int pro_id = pro.getProduct_id();
            boolean isDelete=productService.checkProDelete(pro_id);
            System.out.println(pro_id+"  "+isDelete);
            pro.setDelete(isDelete);
        }
        model.addAttribute("productList",productList);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("search_info",search_info);
        session.setAttribute("pageNumPro",pageNumPro);
        return "searchProduct";
    }

    //商品名智能补全
    @RequestMapping(value = "getFullName",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getFullName(String search_info){
        List<String> nameList=new ArrayList<String>();
        nameList=productService.getFullName(search_info);
        StringBuilder sb=new StringBuilder("");
        if (nameList!=null){
            sb.append("<ul>");
            for (String fullName:nameList){
                sb.append("<li><b>"+fullName+"</b></li>");
            }
            sb.append("</ul>");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    //删除商品
    @RequestMapping("deleteProduct")
    public String deleteProduct(HttpServletRequest request, HttpSession session){
        String pageNumPro=session.getAttribute("pageNumPro").toString();
        String[] chks = request.getParameterValues("chk");

        int[] ids=new int[chks.length];
        for (int i = 0; i < chks.length; i++) {
            ids[i]=Integer.parseInt(chks[i]);
        }

        productService.deleteProduct(ids);
        return "redirect:goToSearchProduct.action?pageNumPro="+pageNumPro;
    }

    //根据关键字查询相关商品（用于用户界面）
    @RequestMapping(value = "/getProductForUserByName/{search_info}",method=RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductForUserByName(@PathVariable String search_info){
        List<Product> productList=new ArrayList<Product>();
        productList=productService.getProductForUserByName(search_info);
        return productList;
    }

    //根据id获得单个商品的外观轮播图
    @RequestMapping(value = "/getAspectForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public List<String> getAspectForUser(@PathVariable int product_id){
        List<String> aspectList=new ArrayList<String>();
        aspectList=productService.getAspectForUser(product_id);
        return aspectList;
    }

    //根据id获得单个商品的信息
    @RequestMapping(value = "/getProductForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public Product getProductForUser(@PathVariable int product_id){
        Product product=new Product();
        product=productService.getProductForUser(product_id);
        return product;
    }

    //根据id获得单个商品的参数图
    @RequestMapping(value = "/getParameterForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public List<String> getParameterForUser(@PathVariable int product_id){
        List<String> parameterList=new ArrayList<String>();
        parameterList=productService.getParameterForUser(product_id);
        return parameterList;
    }

    //按上市日期倒序获得最新手机商品列表
    @RequestMapping("getNewPhone")
    @ResponseBody
    public List<Product> getNewPhone(){
        List<Product> phoneList=new ArrayList<Product>();
        phoneList=productService.getNewPhone();
        return phoneList;
    }

    //按上市日期倒序获得最新电视商品列表
    @RequestMapping("getNewTV")
    @ResponseBody
    public List<Product> getNewTV(){
        List<Product> tvList=new ArrayList<Product>();
        tvList=productService.getNewTV();
        return tvList;
    }

    //按上市日期倒序获得最新笔记本商品列表
    @RequestMapping("getNewPC")
    @ResponseBody
    public List<Product> getNewPC(){
        List<Product> pcList=new ArrayList<Product>();
        pcList=productService.getNewPC();
        return pcList;
    }

    //按上市日期倒序获得最新智能家居商品列表
    @RequestMapping("getNewElec")
    @ResponseBody
    public List<Product> getNewElec(){
        List<Product> elecList=new ArrayList<Product>();
        elecList=productService.getNewElec();
        return elecList;
    }

    //商品名称查重校验
    @RequestMapping(value="checkProName",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String checkProName(String product_name){
        String existCode="";
        boolean isExist=productService.checkProName(product_name);
        if (isExist){
            existCode="此商品名已存在，请输入新的商品名";
        }
        return existCode;
    }

    //范东升部分 end
    /**
     * 根据分类查询下属商品
     * @param classifyID
     * @return
     */
    @RequestMapping(value = "/getProductsByClassifyID/{classifyID}")
    @ResponseBody
    public List<Product> getProducts(@PathVariable int classifyID){
        System.out.println("-----分类查询Controller-----");
//        List<Product> list=productService.getProducts(classifyID);
        return productService.getProducts(classifyID);
    }

//    //展示详细商品
//    @RequestMapping(value = "/showDetailProduct/{productID}")
//    @ResponseBody
//    public void showDetailProduct(@PathVariable int productID){
//        System.out.println("-----商品信息查询Controller-----");
//        productService.storeRecord(productID);
//    }


    /**
     * 根据record获得浏览记录
     * @param record
     * @return
     */
    @RequestMapping(value = "/getRecord/{record}")
    @ResponseBody
    public List<Product> getRecord(@PathVariable String record){
        System.out.println("-----获得记录Controller-----");
        //System.out.println(record);
        return productService.getRecord(record);
    }

    /**
     * 推荐商品
     * @param userid
     * @return
     */
    @RequestMapping(value = "introProduct/{userid}")
    @ResponseBody
    public List<Product> introProduct(@PathVariable int userid){
        System.out.println("-----推荐商品Controller-----");
        return productService.introProduct(userid);
    }
}
