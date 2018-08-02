package com.ssm.controller;


import com.ssm.model.bean.CommentInfo;
import com.ssm.model.bean.Keyword;
import com.ssm.model.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment
     */
    @RequestMapping(value = "addComment")
    public void addComment(CommentInfo comment){
        System.out.println("-----添加评论Controller-----");
        //System.out.println(comment.getCtext());
        commentService.addComment(comment);
    }

    /**
     * 保存评论
     * @param commentInfo
     * @param upload
     * @param request
     * @return
     */

    @RequestMapping(value="/saveComment", method= RequestMethod.POST)
    @ResponseBody
    public String saveComment(CommentInfo commentInfo  , @RequestParam MultipartFile[] upload, HttpServletRequest request){
        System.out.println("in Comtroller saveComment");
        String uploadpath = request.getServletContext().getRealPath("/img");

        System.out.println("UserID: "+commentInfo.getUserID());
        System.out.println("Product_id: "+commentInfo.getProduct_id());
        System.out.println("Score:"+commentInfo.getScore());
        System.out.println("Ctext:"+commentInfo.getCtext());

        commentService.saveComment(commentInfo , upload, uploadpath);
        return "{\"result\":true}";
    }


    /**
     * 根据关键词得到评论
     * @param keyname
     * @return
     */

    @RequestMapping(value = "/getComment/{keyname}")
    @ResponseBody
    public List<CommentInfo> getCommentByKey(@PathVariable String keyname){
        try {
            keyname= java.net.URLDecoder.decode(keyname,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("-----通过key获得评论Controller-----");
        return commentService.getCommentByKey(keyname);
    }



}
