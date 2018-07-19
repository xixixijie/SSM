package com.ssm.model.service;

import com.ssm.model.bean.Comment;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public class CommentService {
    public List<Comment> getCommentByKey(){
        System.out.println("-----通过关键词获取评论service-----");

        return null;
    }

    public void addComment(){
        System.out.println("-----提交评论获取评论service-----");


    }

    public void analysComment(Comment comment){
        System.out.println("-----分析service-----");

    }
}
