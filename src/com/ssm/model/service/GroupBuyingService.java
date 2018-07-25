package com.ssm.model.service;

import com.ssm.model.bean.*;
import com.ssm.model.dao.GroupBuyingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/19.
 */
@Service
public class GroupBuyingService {

    @Autowired
    private GroupBuyingDAO groupBuyingDAO;

    /**
     * 根据查询条件查询符合条件的所有团购活动
     *
     * @return
     */
    public ArrayList<Activity> searchActivities(Activity activity) {
        return groupBuyingDAO.searchActivities(activity);
    }

    /**
     * 根据团购活动号查询参与此团购活动的队伍
     *
     * @param activityID
     * @return
     */
    public ArrayList<Group> searchGroups(int activityID) {
        return groupBuyingDAO.searchGroups(activityID);
    }

    /**
     * 开团操作
     *
     * @param group
     */
    public void initiateGroupBuying(Group group, OpenGroupList openGroupList) {
        groupBuyingDAO.initiateGroupBuying(group);
        int groupID = groupBuyingDAO.getGroupID();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("OpenGroupList",openGroupList);
        map.put("groupID",groupID);
        groupBuyingDAO.initiateGroupBuyingSuffix(map);
    }

    /**
     * 发布团购活动
     */
    public void releaseActivity(Activity activity) {
        groupBuyingDAO.releaseActivity(activity);

    }

    /**
     * 跟团
     *
     * @param joinGroupList
     */
    public void joinGroupBuying(JoinGroupList joinGroupList) {
        groupBuyingDAO.joinGroupBuying(joinGroupList);
    }

    /**
     * 检查是否已经达到要求参与的人数
     *
     * @param groupID
     * @return
     */
    public boolean checkIfEnough(int groupID) {
        int nowNum = groupBuyingDAO.checkIfEnough(groupID);
        int requiredNum = groupBuyingDAO.getRequiredNumber(groupID);
        return nowNum == requiredNum;
    }

    /**
     * 检查是否可以删除，如果已经开始就不能删除并打个标记
     *
     * @param activities
     * @return
     */
    public ArrayList<Activity> checkIfCanDelete(ArrayList<Activity> activities) {
        for (Activity activity :activities) {
            Date groupStartDate = groupBuyingDAO.checkIfCanDelete(activity);
            Date nowDate = new Date();
            if(nowDate.getTime()-groupStartDate.getTime()>0){
                //尚未开始
                activity.setCanDelete(true);
            }else {
                //已经开始
                activity.setCanDelete(false);
            }
        }
        return activities;
    }   

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为3（被删除）
     *
     * @param a_id
     */
    public void deleteActivities(int[] a_id) {

    }

    /**
     * 根据groupID取得所有的参与的UserID
     * @param groupID
     * @return
     */
    public ArrayList<Integer> searchJoiners(int groupID) {
        ArrayList<Integer> userIDs = groupBuyingDAO.searchJoiners(groupID);
        userIDs.add(groupBuyingDAO.searchLeader(groupID));
        return userIDs;
    }

    /**
     * 根据groupID查询团购队伍的相关信息
     * @param groupID
     * @return
     */
    public Group searchGroupInfo(int groupID) {
        return groupBuyingDAO.searchGroupInfo(groupID);
    }

    /**
     * 根据activityID查询团购活动的相关信息
     * @param activityID
     * @return
     */
    public Activity searchActivityInfo(int activityID) {
        return groupBuyingDAO.searchActivityInfo(activityID);
    }


    /**
     * 获得排序最高，价格最低的团购活动
     * @return
     */
    public Activity getRecommendedGroupBuying() {
        return groupBuyingDAO.getRecommendedGroupBuying().get(0);
    }

    /**
     * 修改团购信息
     * @param activityID
     * @param requiredNumber
     * @param group_buying_price
     */
    public void modifyActivityInfo(int activityID, int requiredNumber, double group_buying_price) {
        Activity activity = new Activity();
        activity.setActivityID(activityID);
        activity.setRequiredNumber(requiredNumber);
        activity.setGroup_buying_price(group_buying_price);
        groupBuyingDAO.modifyActivityInfo(activity);
    }
}

