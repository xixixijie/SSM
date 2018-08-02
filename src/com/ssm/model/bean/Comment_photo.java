package com.ssm.model.bean;

/**
 * Created by WANGKING on 2018-07-19.
 */
public class Comment_photo {

    private int photo_id;
    private int cid;
    private String photourl;

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
