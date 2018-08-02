package com.ssm.model.bean;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 * 团购队伍实体类
 *
 * @author chenyufeng
 */

public class Group {
    private int groupID;                    //团队ID，用于唯一区分团购队伍
    private UserInfo leader;                //开团者，开团者的相关信息封装在UserInfo对象中
    private Activity activity;              //对应的团购活动，对应的团购活动的相关信息封装在Activity对象中
    private int current_num;                //此团购队伍当前的参与人数
    private int state;                      //0失败  1进行中  2成功


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
