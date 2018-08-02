package com.ssm.model.bean;

/**
 * Created by xixi on 2018/7/19.
 */
public class Keyword {
    private int keyID;
    private String keyName;
    private int keyNum;
    private int labelID;

    public int getLabelID() {
        return labelID;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public int getKeyID() {
        return keyID;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(int keyNum) {
        this.keyNum = keyNum;
    }
}
