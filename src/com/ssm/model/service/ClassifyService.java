package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.dao.ClassifyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class ClassifyService {

    @Autowired
    private ClassifyDAO classifyDAO;

    public void deleteClassify(int classifyID){
        System.out.println("-----删除分类-----");
        classifyDAO.deleteClassify(classifyID);
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
