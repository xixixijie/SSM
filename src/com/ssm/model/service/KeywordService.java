package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Keyword;
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

    public List<Keyword> getTopKeys() {
        System.out.println("-----获得最高关键词Service-----");
        List<Keyword> list=keywordDAO.getAllKeyword();
        Collections.sort(list, new Comparator<Keyword>() {
            @Override
            public int compare(Keyword o1, Keyword o2) {
                int x=o1.getKeyNum();
                int y=o2.getKeyNum();
                return (x > y) ? -1 : ((x == y) ? 0 : 1);

            }
        });
        //小于5全返回
        if(list.size()<=5){
            return list;
        }
        List<Keyword> newList=new ArrayList<>();
        for(int i=0;i<5;i++){
            newList.add(list.get(i));
        }
        return newList;
    }
}
