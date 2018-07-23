package com.ssm.model.dao;

import com.ssm.model.bean.Classify;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface ClassifyDAO {

    public void deleteClassify(String[] classifyID);

    public void addClassify(Classify classify);

    public Classify getClassify(int classifyID);

    public void ModifyClassify(Classify classify);


    public List<Classify> showClassify();

    public List<Classify> findClassify(String content);

    List<Classify> getClassifys(String[] ids);
}
