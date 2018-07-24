package com.ssm.model.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class Group {
    private int groupID;
    private UserInfo leader;
    private Activity activity;
    private int current_num;
    private int state;


    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public UserInfo getLeader() {
        return leader;
    }

    public void setLeader(UserInfo leader) {
        this.leader = leader;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getCurrent_num() {
        return current_num;
    }

    public void setCurrent_num(int current_num) {
        this.current_num = current_num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
