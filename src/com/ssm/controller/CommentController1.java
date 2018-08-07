package com.ssm.controller;

import com.ssm.model.bean.CommentInfo;
import com.ssm.model.service.CommentService;
import nu.xom.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommentController1 {


    @Autowired
    private CommentService service;

    /**
     * 通过关键词获取评论
     * @param keylabel
     * @return
     */
    @RequestMapping("getCommentBykey/{keylabel}")
    @ResponseBody
    public List<CommentInfo> getCommentByKet(@PathVariable String keylabel){

        System.out.println("关键词id"+keylabel);
        List<CommentInfo> list=service.getCommentByKey(keylabel);

        return list;
    }

}
