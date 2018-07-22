package com.ssm.controller;

import com.ssm.model.bean.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class CommentController {


    @RequestMapping(value = "addComment")
    public void addComment(Comment comment){

    }






    public List<Comment> getCommentByKey(){
        System.out.println("-----通过key获得评论Controller-----");

        return null;
    }

}
