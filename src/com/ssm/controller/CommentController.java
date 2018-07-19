package com.ssm.controller;

import com.ssm.model.bean.Comment;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class CommentController {

    public List<Comment> getCommentByKey(){
        System.out.println("-----通过key获得评论Controller-----");

        return null;
    }

}
