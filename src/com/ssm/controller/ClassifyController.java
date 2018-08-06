package com.ssm.controller;

import com.ssm.model.bean.BuyRecord;
import com.ssm.model.bean.Classify;
import com.ssm.model.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 * 分类控制器
 */
@Controller
public class ClassifyController {
    @Autowired
    private ClassifyService classifyService;

    /**
     * 根据内容查找分类，返回分类list
     * @param content
     * @return
     */
    @RequestMapping(value = "findClassify/{content}",produces={"application/json;","text/html;charset=UTF-8;"})
    @ResponseBody
    public List<Classify> findClassify(@PathVariable String content){
        System.out.println("-----查找分类Controller-----");
        try {
            content= java.net.URLDecoder.decode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(content.equals("void"))
            content="";
        return classifyService.findClassify(content);

    }

    /**
     * 根据id数组，删除相应分类
     * @param ids
     * @return
     */

    @RequestMapping(value = "deleteClassify/{ids}")
    @ResponseBody
    public String deleteClassify(@PathVariable String ids){
        System.out.println("-----删除分类Controller-----");
        classifyService.deleteClassify(ids.split(","));
        return "{\"result\":true}";

    }

    /**
     * 添加分类
     * @param classify
     * @return
     */
    @RequestMapping(value = "addClassify")
    @ResponseBody
    public String addClassify(Classify classify){
        System.out.println("-----加分类Controller-----");
        classifyService.addClassify(classify);
        return "{\"result\":true}";

    }

    /**
     * 根据分类id 获得分类
     * @param classifyID
     * @return
     */

    @RequestMapping(value = "getClassify/{classifyID}",produces={"application/json;","text/html;charset=UTF-8;"})
    @ResponseBody
    public Classify getClassify(@PathVariable int classifyID){
        System.out.println("-----获得分类Controller-----");


        return classifyService.getClassify(classifyID);
    }

    /**
     * 修改分类信息
     * @param classify
     * @return
     */
    @RequestMapping(value = "modifyClassify")
    @ResponseBody
    public String ModifyClassify(Classify classify){
        System.out.println("-----修改分类Controller-----");
        classifyService.ModifyClassify(classify);
       // mav.addObject("resultList",list);
        return "{\"result\":true}";

    }

    /**
     * 获得所有分类
     * @return
     */
    @RequestMapping(value = "showClassify")
    @ResponseBody
    public List<Classify> showClassify(){
        System.out.println("-----展示分类Controller-----");
//        System.out.println(type);
        return classifyService.showClassify();

    }

    /**
     * 根据分类类型 和 ids数组 ，进行排序
     * @param type
     * @param ids
     * @return
     */

    @RequestMapping(value = "sortClassify/{type}/{ids}")
    @ResponseBody
    public List<Classify> sortClassify(@PathVariable int type,@PathVariable String ids){
        System.out.println("-----排序分类Controller-----");
        System.out.println(ids.split(",").length);
        return classifyService.sortClassify(type,ids.split(","));

    }

    /**
     * 更新向量模型
     * @return
     */

    @RequestMapping(value = "updateModel")
    @ResponseBody
    public String updateModel(){
        System.out.println("更新模型Controller");
        classifyService.updateModel();
        return "{\"result\":true}";
    }

    @RequestMapping(value = "getSimilarity/{word1}/{word2}")
    @ResponseBody
    public String getSimilarity(@PathVariable String word1,@PathVariable String word2){

        try {
            word1= java.net.URLDecoder.decode(word1,"UTF-8");
            word2= java.net.URLDecoder.decode(word2,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(word1+" "+word2);
        double similarity=classifyService.getSimilarity(word1,word2);
        System.out.println(similarity);

        return "{\"result\":"+similarity+"}";
    }

    @RequestMapping(value = "getBuyRecord")
    @ResponseBody
    public List<BuyRecord> getBuyRecord(){
        //System.out.println("getRecordIn");
        return classifyService.getBuyRecord();
    }


}
