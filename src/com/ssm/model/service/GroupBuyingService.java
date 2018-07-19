package com.ssm.model.service;

import com.ssm.model.bean.Activity;
import com.ssm.model.bean.Group;
import com.ssm.model.dao.GroupBuyingDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class GroupBuyingService {

    @Autowired
    private GroupBuyingDAO groupBuyingDAO;

    /**
     * 根据查询条件查询符合条件的所有团购活动
     * @return
     */
    public ArrayList<Activity> searchActivities(Activity activity){
        return groupBuyingDAO.searchActivities(activity);
    }

    //
    /**
     * 根据团购活动号查询参与此团购活动的队伍
     * @param activityID
     * @return
     */
    public ArrayList<Group> searchGroups(int activityID){
        return null;
    }

    /**
     *开团操作
     * @param group
     */
    public void initiateGroupBuying(Group group){

    }

    /**
     * 发布团购活动
     */
    public void releaseActivity(){

    }

    /**
     * 跟团
     * @param groupID
     * @param userID
     */
    public void joinGroupBuying(int groupID,int userID){

    }

    /**
     * 检查是否已经达到要求参与的人数
     * @param groupID
     * @return
     */
    public boolean checkIfEnough(int groupID){
        return false;
    }

    /**
     * 检查是否可以删除，如果已经开始就不能删除并打个标记
     * @param activities
     * @return
     */
    public ArrayList<Activity> checkIfCanDelete(ArrayList<Activity> activities){
        return null;
    }

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为3（被删除）
     * @param a_id
     */
    public void deleteActivities(int[] a_id){

    }

}

