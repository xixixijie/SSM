package com.ssm.controller;

import com.ssm.model.bean.Classify;
import com.ssm.model.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class ClassifyController {
    @Autowired
    private ClassifyService classifyService;

    public void deleteClassify(int classifyID){

    }

    public void addClassify(Classify classify){

    }

    public Classify getClassify(){
        return null;
    }

    public void ModifyClassify(Classify classify){

    }

    public List<Classify> showClassify(){
        return null;
    }
}
