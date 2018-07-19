package com.ssm.controller;

import com.ssm.model.bean.Activity;
import com.ssm.model.bean.Group;
import com.ssm.model.service.GroupBuyingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by chenyufeng on 2018/7/19.
 */
public class GroupBuyingController {
    @Autowired
    private GroupBuyingService groupBuyingService;

    /**
     * 根据搜索条件搜索对应的活动集合
     * @return
     */
    public ArrayList<Activity> searchActivities(){
        return null;
    }

    /**
     * 根据活动ID搜索已经参加此团购活动的团队
     * @param activity_id
     * @return
     */
    public ArrayList<Group> searchGroups(int activity_id){
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
