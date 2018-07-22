package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class ClassifyController {
    @Autowired
    private ClassifyService classifyService;


    @RequestMapping(value = "findClassify/{content}")
    @ResponseBody
    public List<Classify> findClassify(@PathVariable String content){
        System.out.println("-----系统展示Controller-----");
        return classifyService.findClassify(content);

    }

    @RequestMapping(value = "deleteClassify/{ids}")
    public void deleteClassify(@PathVariable String ids){
        System.out.println("-----系统展示Controller-----");
        classifyService.deleteClassify(ids.split(","));

    }

    @RequestMapping(value = "addClassify")
    @ResponseBody
    public String addClassify(Classify classify){
        System.out.println("-----加分类Controller-----");
        classifyService.addClassify(classify);

        return "{\"result\":true}";

    }

    @RequestMapping(value = "getClassify/{classifyID}")
    @ResponseBody
    public Classify getClassify(@PathVariable int classifyID){
        System.out.println("-----获得分类Controller-----");


        return classifyService.getClassify(classifyID);
    }

    @RequestMapping(value = "ModifyClassify")
    @ResponseBody
    public String ModifyClassify(Classify classify){
        System.out.println("-----修改分类Controller-----");
        classifyService.ModifyClassify(classify);
       // mav.addObject("resultList",list);

        return "{\"result\":true}";

    }
    @RequestMapping(value = "showClassify/{type}")
    @ResponseBody
    public List<Classify> showClassify(@PathVariable int type){
        System.out.println("-----展示分类Controller-----");
        return classifyService.showClassify(type);

    }
}
