package com.ssm.model.dao;


import com.ssm.model.bean.CommentInfo;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface CommentDAO {
    public List<CommentInfo> getCommentByKey(String keyName);
    public int getCommentNum(int productID);

    void addComment(CommentInfo comment);
}
