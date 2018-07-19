package com.ssm.model.bean;

/**
 * Created by xixi on 2018/7/13.
 */
public class Classify {
    private int classifyID;
    private String className;
    private String classDis;
    private int classState;

    public int getClassifyID() {
        return classifyID;
    }

    public void setClassifyID(int classifyID) {
        this.classifyID = classifyID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDis() {
        return classDis;
    }

    public void setClassDis(String classDis) {
        this.classDis = classDis;
    }

    public int getClassState() {
        return classState;
    }

    public void setClassState(int classState) {
        this.classState = classState;
    }
}
