package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    public void deleteClassify(int classifyID){
        System.out.println("-----系统展示Controller-----");


    }

    public void addClassify(Classify classify){
        System.out.println("-----加分类Controller-----");

    }

    public Classify getClassify(int classifyID){
        System.out.println("-----获得分类Controller-----");


        return null;
    }

    @RequestMapping(value = "ModifyClassify")
    public void ModifyClassify(Classify classify){
        System.out.println("-----修改分类Controller-----");


    }
    @RequestMapping(value = "showClassify")
    @ResponseBody
    public List<Classify> showClassify(){
        System.out.println("-----展示分类Controller-----");
        List<Classify> list=classifyService.showClassify();
        System.out.println("返回分类大小"+list.size());

        return list;

    }
}
