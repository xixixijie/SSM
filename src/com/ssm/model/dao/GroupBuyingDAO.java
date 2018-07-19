package com.ssm.model.dao;

import com.ssm.model.bean.Activity;
import com.ssm.model.bean.Group;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public interface GroupBuyingDAO {
    /**
     * 根据查询条件查询符合条件的所有团购活动
     * @return
     */
    public ArrayList<Activity> searchActivities(Activity activity);

    /**
     * 根据团购活动号查询参与此团购活动的队伍
     * @param activityID
     * @return
     */
    public ArrayList<Group> searchGroups(int activityID);

    /**
     *开团操作
     * @param group
     */
    public void initiateGroupBuying(Group group);

    /**
     * 发布团购活动
     */
    public void releaseActivity(Activity activity);

    /**
     * 跟团
     * @param groupID
     * @param userID
     */
    public void joinGroupBuying(int groupID, int userID);

    /**
     * 检查是否已经达到要求参与的人数
     * DAO层做的主要工作就是返回当前人数
     * @param groupID
     * @return
     */
    public int checkIfEnough(int groupID);


    /**
     * 配合上面的函数checkIfEnough
     * 此函数的作用在于返回活动预先设定的需要的参与人数
     * @return
     */
    public int getRequiredNumber(int groupID);


    /**
     * 检查是否可以删除，如果已经开始就不能删除，DAO层做的工作就是返回其开始时间
     * @param activity
     * @return
     */
    public Date checkIfCanDelete(Activity activity);

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为3（被删除）
     * @param a_id
     */
    public void deleteActivities(int[] a_id);
}

