package com.ssm.model.bean;

/**
 * Created by chenyufeng on 2018/8/6.
 */
public class ActivityForCount {
    private Activity activity;
    private int activityID;
    private int count;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
