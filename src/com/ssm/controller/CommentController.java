package com.ssm.controller;


import com.ssm.model.bean.CommentInfo;
import com.ssm.model.bean.Keyword;
import com.ssm.model.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "addComment")
    public void addComment(CommentInfo comment){
        System.out.println("-----添加评论Controller-----");
        commentService.addComment(comment);
    }





    @RequestMapping(value = "/addComment/{keyname}")
    @ResponseBody
    public List<CommentInfo> getCommentByKey(@PathVariable String keyname){
        System.out.println("-----通过key获得评论Controller-----");
        return commentService.getCommentByKey(keyname);
    }



}
