package com.ssm.model.dao;

import com.ssm.model.bean.Keyword;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface KeywordDAO {
    public void addKeyword(String keyword);

    public List<Keyword> getAllKeyword();

    void addNum(int keyID);
}
