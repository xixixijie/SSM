package com.ssm.controller;

import com.ssm.model.bean.Keyword;
import com.ssm.model.bean.keyLabel;
import com.ssm.model.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

/**
 * Created by xixi on 2018/7/22.
 */
@Controller
public class KeywordController {
    @Autowired
    private KeywordService keywordService;

    /**
     * 获得评论数最高的关键标签
     * @return
     */
    @RequestMapping(value = "getKeyLabel")
    @ResponseBody
    public List<keyLabel> getTopKeys(){
        System.out.println("-----获得最高的标签Controller-----");
        return keywordService.getTopKeys();
    }

}
