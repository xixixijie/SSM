package com.ssm.controller;

import com.ssm.model.bean.Keyword;
import com.ssm.model.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.List;

/**
 * Created by xixi on 2018/7/22.
 */
@Controller
public class KeywordController {
    @Autowired
    private KeywordService keywordService;

    public List<Keyword> getTopKeys(){
        System.out.println("-----获得最高的关键词Controller-----");
        return keywordService.getTopKeys();
    }

}
