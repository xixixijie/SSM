package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Keyword;
import com.ssm.model.bean.keyLabel;
import com.ssm.model.dao.KeywordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

/**
 * Created by xixi on 2018/7/22.
 */
@Service
public class KeywordService {
    @Autowired
    private KeywordDAO keywordDAO;

    /**
     * 获得评论数最多的关键标签
     * @return
     */

    public List<keyLabel> getTopKeys() {
        System.out.println("-----获得最高关键标签Service-----");
        List<keyLabel> list=keywordDAO.getAllKeyLabel();

        //小于5全返回
        if(list.size()<=5){
            return list;
        }
        List<keyLabel> newList=new ArrayList<>();
        for(int i=0;i<5;i++){
            newList.add(list.get(i));
        }
        return newList;
    }
}
