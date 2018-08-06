package com.ssm.model.bean;

/**
 * Created by WANGKING on 2018-07-20.
 */
public class Praise {
    private int praise_id;
    private int cid;//评论编号
    private int userid;//用户编号
    private int is_praise;//是否已点赞，0：否，1：是

    public int getPraise_id() {
        return praise_id;
    }

    public void setPraise_id(int praise_id) {
        this.praise_id = praise_id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(int is_praise) {
        this.is_praise = is_praise;
    }
}
