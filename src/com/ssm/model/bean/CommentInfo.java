package com.ssm.model.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by xixi on 2018/7/19.
 */
public class CommentInfo {
    private String ctext;

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    /*wym,begin,2018年7月23日*/
    private Integer cid;//评论编号
    private int userID;//评论用户
    private String userName;

    private float score;//评分

    private Integer product_id;//评论的商品
    private Integer praise;//点赞数
    private Date cdate;//评论日期
    private List<Reply> reply;//评论的回复
    private String photourl;
    private List<Comment_photo> cphoto;//评论的图片
    private Praise praiseInfo;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public List<Comment_photo> getCphoto() {
        return cphoto;
    }

    public void setCphoto(List<Comment_photo> cphoto) {
        this.cphoto = cphoto;
    }

    public Praise getPraiseInfo() {
        return praiseInfo;
    }

    public void setPraiseInfo(Praise praiseInfo) {
        this.praiseInfo = praiseInfo;
    }
}
