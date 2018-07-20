package com.ssm.model.dao;

import com.ssm.model.bean.Comment;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface CommentDAO {
    public List<Comment> getCommentByKey(String keyName);
    public int getCommentNum(int productID);
}
