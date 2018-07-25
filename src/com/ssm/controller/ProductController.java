package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Product;
import com.ssm.model.dao.ProductDAO;
import com.ssm.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String addProduct(@ModelAttribute Product product, @RequestParam MultipartFile cover, @RequestParam MultipartFile[] aspectPics, @RequestParam MultipartFile[] parameterPics){

        String oldAspectName="";
        String oldParameterName="";
        String oldCoverName="";

        String newAspectName="";
        String newParameterName="";
        String newCoverName="";

        String aspectUrl="D:\\ABC/aspect/";
        String parameterUrl="D:\\ABC/parameter/";
        String coverUrl="D:\\\\ABC/cover/";

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

        product.setCover_url(cover_url);

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
                productService.addAspect(aspect_url,product_id);
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
                productService.addParameter(parameter_url,product_id);

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

        return "redirect:goToSearchProduct.action?pageNumPro="+pageNumPro;
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
