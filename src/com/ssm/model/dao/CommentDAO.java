package com.ssm.model.dao;


import com.ssm.model.bean.CommentInfo;
import com.ssm.model.bean.Comment_photo;
import com.ssm.model.bean.Praise;
import com.ssm.model.bean.Reply;

import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public interface CommentDAO {
    public List<CommentInfo> getCommentByKey(String keyName);
    public int getCommentNum(int productID);

    void addComment(CommentInfo comment);


    /*以下方法为wym建立*/
    public List<CommentInfo> getAllComment();
    public List<CommentInfo> getPopularComment(int product_id); //获取热门评论
    public List<Comment_photo> getCommentPhoto(int cid);//获得图片
    public List<Reply> getReply(int cid);//获得回复
    public CommentInfo getCommentById(int cid);//根据cid获取评论
    public void saveComment(CommentInfo comment); //保存评论
    public void savePhoto(Comment_photo photo);//保持图片
    public void saveReply(Reply reply);//保存评论

    public void updateComment(CommentInfo comment);//更新评论（点赞数）
    public Praise getPraise(Praise praise); //获得点赞信息
    public void updatePraise(Praise praise);//修改点赞的信息
    public void savePraise(Praise praise);//添加点赞的信息
    public int getMaxCid();//获取cid的最大值

    String getUserNameByID(int userID);
    /*wym,end*/
}
