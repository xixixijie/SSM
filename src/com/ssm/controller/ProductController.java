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
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    //范东升 begin
    @RequestMapping("goToAddProduct")
    public String goToAddProduct(Model model){
        List<Classify> classifyList=new ArrayList<Classify>();
        classifyList=productService.getAllClassify();


        model.addAttribute("classifyList",classifyList);
        return "addProduct";
    }

    @RequestMapping(value="addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product,@RequestParam MultipartFile cover, @RequestParam MultipartFile[] aspectPics, @RequestParam MultipartFile[] parameterPics){

        String oldAspectName="";
        String oldParameterName="";
        String oldCoverName="";

        String newAspectName="";
        String newParameterName="";
        String newCoverName="";

        String aspectUrl="D:\\javaWeb/SSM/WebContent/img";
        String parameterUrl="D:\\javaWeb/SSM/WebContent/img";
        String coverUrl="D:\\javaWeb/SSM/WebContent/img";

        String aspect_url="";
        String parameter_url="";
        String cover_url="";



        oldCoverName=cover.getOriginalFilename();
        newCoverName=System.currentTimeMillis()+oldCoverName.substring(oldCoverName.indexOf("."));
        File coverfile = new File(coverUrl,newCoverName);
        try {
            cover.transferTo(coverfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cover_url=coverUrl+newCoverName;

        product.setCover_url(newCoverName);

//        System.out.println(product.getProduct_name()+"  "+product.getClassify().getClassifyID()
//                +"  "+product.getOriginal_price()+"  "+product.getDiscount_price()+"  "+product.getProduct_info()+"  "
//                +product.getCover_url()
//        );
        productService.addProduct(product);
        int product_id=productService.getCurrentProductId();



        for (int i = 0; i < aspectPics.length; i++) {
            oldAspectName = aspectPics[i].getOriginalFilename();
            newAspectName = System.currentTimeMillis()+oldAspectName.substring(oldAspectName.indexOf("."));

            File file = new File(aspectUrl,newAspectName);
            try {
                aspectPics[i].transferTo(file);
                aspect_url=aspectUrl+newAspectName;
                productService.addAspect(newAspectName,product_id);
//                System.out.println(aspect_url);
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
                parameter_url=parameterUrl+newParameterName;
                productService.addParameter(newParameterName,product_id);

//                System.out.println(parameter_url);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:goToAddProduct.action";
    }

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
//        for (Product product:productList) {
//            System.out.println(product.getProduct_id()+"  "+product.getProduct_name());
//       }

        model.addAttribute("productList",productList);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("pageNumPro",pageNumPro);
        return "searchProduct";
    }

    @RequestMapping("getProductInfo")
    public String getProductInfo(int product_id,Model model){
        Product product=new Product();
        product=productService.getProductById(product_id);
        model.addAttribute("product",product);
        System.out.println("进入getProdcutInfo.action");
        return "productInfo";
    }

    @RequestMapping("editProduct")
    public String editProduct(Model model,int product_id){
        List<Classify> classifyList=new ArrayList<Classify>();
        classifyList=productService.getAllClassify();

        Product product=new Product();
        product=productService.getProductById(product_id);

        model.addAttribute("classifyList",classifyList);
        model.addAttribute("product",product);
        System.out.println("进入editProduct.action");
        return "editProduct";
    }

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

    @RequestMapping("getProductByName")
    public String getProductByName(String search_info,int pageNumPro,Model model,HttpSession session){
        int pageCount=0;
//        int pageNumPro=1;
        List<Product> productList=new ArrayList<Product>();
        if (search_info != null && !"".equals(search_info)){
            productList=productService.getProductByName(search_info,pageNumPro);
            pageCount=productService.getPageCountByName(search_info);
        }else {
            productList=productService.getAllPageProduct(pageNumPro);
            pageCount=productService.getAllPageCount();
        }

//        for (Product product:productList) {
//            System.out.println(product.getProduct_id()+"  "+product.getProduct_name());
//       }

        model.addAttribute("productList",productList);
        model.addAttribute("pageCount",pageCount);
        session.setAttribute("search_info",search_info);
        session.setAttribute("pageNumPro",pageNumPro);
        return "searchProduct";
    }

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

    @RequestMapping("deleteProduct")
    public String deleteProduct(HttpServletRequest request, HttpSession session){
        String pageNumPro=session.getAttribute("pageNumPro").toString();
        String[] chks = request.getParameterValues("chk");
//        for (int i = 0; i < chks.length; i++) {
//            System.out.println(chks[i]);
//        }
        int[] ids=new int[chks.length];
        for (int i = 0; i < chks.length; i++) {
            ids[i]=Integer.parseInt(chks[i]);
        }
        productService.deleteProduct(ids);
        return "redirect:goToSearchProduct.action?pageNumPro="+pageNumPro;
    }

    @RequestMapping(value = "/getProductForUserByName/{search_info}",method=RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductForUserByName(@PathVariable String search_info){
        List<Product> productList=new ArrayList<Product>();
        productList=productService.getProductForUserByName(search_info);
        return productList;
    }

    @RequestMapping(value = "/getAspectForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public List<String> getAspectForUser(@PathVariable int product_id){
        List<String> aspectList=new ArrayList<String>();
        aspectList=productService.getAspectForUser(product_id);


        return aspectList;
    }

    @RequestMapping(value = "/getProductForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public Product getProductForUser(@PathVariable int product_id){
        Product product=new Product();
        product=productService.getProductForUser(product_id);
        return product;
    }

    @RequestMapping(value = "/getParameterForUser/{product_id}",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public List<String> getParameterForUser(@PathVariable int product_id){
        List<String> parameterList=new ArrayList<String>();
        parameterList=productService.getParameterForUser(product_id);
        return parameterList;
    }


    @RequestMapping("getNewPhone")
    @ResponseBody
    public List<Product> getNewPhone(){
        List<Product> phoneList=new ArrayList<Product>();
        phoneList=productService.getNewPhone();
        return phoneList;
    }

    @RequestMapping("getNewTV")
    @ResponseBody
    public List<Product> getNewTV(){
        List<Product> tvList=new ArrayList<Product>();
        tvList=productService.getNewTV();
        return tvList;
    }

    @RequestMapping("getNewPC")
    @ResponseBody
    public List<Product> getNewPC(){
        List<Product> pcList=new ArrayList<Product>();
        pcList=productService.getNewPC();
        return pcList;
    }

    @RequestMapping("getNewElec")
    @ResponseBody
    public List<Product> getNewElec(){
        List<Product> elecList=new ArrayList<Product>();
        elecList=productService.getNewElec();
        return elecList;
    }

    //范东升 end

    @RequestMapping(value = "/getProductsByClassifyID/{classifyID}")
    @ResponseBody
    public List<Product> getProducts(@PathVariable int classifyID){
        System.out.println("-----分类查询Controller-----");
//        List<Product> list=productService.getProducts(classifyID);
        return productService.getProducts(classifyID);
    }

    //展示详细商品
    @RequestMapping(value = "/showDetailProduct/{productID}")
    @ResponseBody
    public void showDetailProduct(@PathVariable int productID){
        System.out.println("-----商品信息查询Controller-----");
        productService.storeRecord(productID);
    }


    @RequestMapping(value = "/getRecord/{record}")
    @ResponseBody
    public List<Product> getRecord(@PathVariable String record){
        System.out.println("-----获得记录Controller-----");
        //System.out.println(record);
        return productService.getRecord(record);
    }

    @RequestMapping(value = "introProduct/{userid}")
    @ResponseBody
    public List<Product> introProduct(@PathVariable int userid){
        System.out.println("-----推荐商品Controller-----");
        return productService.introProduct(userid);
    }
}
