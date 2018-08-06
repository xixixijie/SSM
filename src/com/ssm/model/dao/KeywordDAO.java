package com.ssm.model.dao;

import com.ssm.model.bean.keyLabel;
import com.ssm.model.bean.Keyword;

import java.util.List;
import java.util.Map;

/**
 * Created by xixi on 2018/7/19.
 */
public interface KeywordDAO {
    public void addKeyword(Keyword content);

    public List<keyLabel> getAllKeyLabel();



    void addNum(Map map);

    void addKeyLabel(keyLabel keyLabel);


    int getMaxLabelID();

    void deleteKeyword();
    void deleteKeylabel();

    keyLabel getKeyLabel(int labelID);

    void updateLabel(keyLabel label);

    List<Keyword> getAllKeyword();

    List<Keyword> getKeywordByLabelID(int labelID);
}
