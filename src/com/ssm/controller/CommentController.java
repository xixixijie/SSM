package com.ssm.controller;


import com.ssm.model.bean.CommentInfo;
import com.ssm.model.bean.Keyword;
import com.ssm.model.bean.Praise;
import com.ssm.model.bean.Reply;
import com.ssm.model.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
        //System.out.println(comment.getCtext());
        commentService.addComment(comment);
    }





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

    /* wym,begin,2018年7月23日 */
    @RequestMapping("test")
    public String test(HttpSession session){
        System.out.println("----------in CONTROLLER test-------------");
        commentService.test();

////getPopularComment
//        {
//            List<CommentInfo> list=commentService.getPopularComment(1,1,2);
//            System.out.println(list);
//            for (CommentInfo c:list){
//                System.out.println("------------------------------------");
//                System.out.println("c.getCid() "+c.getCid());
//                System.out.println("c.getUserID() "+c.getUserID());
//                System.out.println("c.getPraise() "+c.getPraise());
//                System.out.println("c.getCdate() "+c.getCdate());
//                System.out.println("c.getCtext() "+c.getCtext());
//                for (Comment_photo cp:c.getCphoto()){
//                    System.out.println("cp.getPhoto_id() "+cp.getPhoto_id());
//                    System.out.println("cp.getPhotourl() "+cp.getPhotourl());
//                }
//                for(Reply r:c.getReply()){
//                    System.out.println("r.getRid() "+r.getRid());
//                    System.out.println(" r.getRtext()"+r.getRtext());
//                    System.out.println("r.getUserid() "+r.getUserid());
//                    System.out.println("r.getRdate() "+r.getRdate());
//                }
//                System.out.println("c.getPraiseInfo() "+c.getPraiseInfo());
//            }
//        }

//changePraise
//        {
//            CommentInfo c = new CommentInfo();
//            c.setCid(1);
//            c.setPraise(666);
////            Praise p =null;
//            Praise p = new Praise();
//            p.setIs_praise(1);
//            p.setUserid(5);
//            p.setCid(c.getCid());
//            c.setPraiseInfo(p);
//            commentService.changePraise(c,5);
//        }


        session.setAttribute("result","END");
        return "redirect:wymTest.jsp";
    }


    @RequestMapping("getPopularComment")
    @ResponseBody
    public List<CommentInfo> getPopularComment(int page, int product_id,int userID, HttpSession session){
        System.out.println("in controller getPopularComment");
        List<CommentInfo> list = commentService.getPopularComment(page,product_id,userID);
        session.setAttribute("commentList",list);
        System.out.println("----------list.size():"+list.size());

        return list;
    }


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

    @RequestMapping("saveReply")
    public String saveReply(Reply reply){
        System.out.println("in controller saveReply");
        commentService.saveReply(reply);

        return "redirect:showComment.jsp";
    }

    @RequestMapping("changePraise")
    public String changePraise(CommentInfo commentInfo,int userID){
        System.out.println("in controller changePraise");
        commentService.changePraise(commentInfo,userID);
        return "redirect:showComment.jsp";
    }

    @RequestMapping("getPraise")
    public String getPraise(Praise praise){
        System.out.println("in controller getPraise");
        commentService.getPraise(praise);

        return "redirect:showComment.jsp";
    }


    /*wym,end*/

}
