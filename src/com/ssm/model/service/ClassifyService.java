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
        System.out.println("-----删除分类service-----");
        classifyDAO.deleteClassify(classifyID);
    }

    public void addClassify(Classify classify){
        System.out.println("-----添加分类service-----");
        classifyDAO.addClassify(classify);

    }

    public Classify getClassify(int classifyID){
        System.out.println("-----获取分类service-----");
        classifyDAO.getClassify(classifyID);
        return null;
    }

    public void ModifyClassify(Classify classify){
        System.out.println("-----修改分类service-----");
        classifyDAO.ModifyClassify(classify);

    }

    public List<Classify> showClassify(){
        System.out.println("-----展示分类service-----");
        List<Classify> list=classifyDAO.showClassify();
        if(list.size()==0||list==null){
            System.out.println("-----展示分类失败-----");
        }
        return list;
    }
}
