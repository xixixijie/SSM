package com.ssm.controller;

import com.ssm.model.bean.*;
import com.ssm.model.service.GroupBuyingService;
import com.ssm.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/19.
 */
@Controller
public class GroupBuyingController {
    @Autowired
    private GroupBuyingService groupBuyingService;
    @Autowired
    private MessageService messageService;

    /**
     * 根据搜索条件搜索对应的活动集合
     *
     * @return
     */
    @RequestMapping(value = "/searchActivities/{productName}/{groupStartDate}/{groupEndDate}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Activity> searchActivities(@PathVariable String productName,
                                                @PathVariable String groupStartDate, @PathVariable String groupEndDate
    ) {
        System.out.println(productName);
        System.out.println(groupStartDate);
        System.out.println(groupEndDate);
        Activity activity = new Activity();

        Date startDate = null;
        if (groupStartDate != null & !"-1".equals(groupStartDate)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse(groupStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activity.setGroupStartDate(startDate);
        }
        Date endDate = null;
        if (groupEndDate != null & !"-1".equals(groupEndDate)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                endDate = sdf.parse(groupEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activity.setGroupEndDate(endDate);
        }
        Product product = new Product();
        if (productName != null & !"-1".equals(productName)) {
            product.setProduct_name(productName);
        }
        activity.setProduct(product);
        ArrayList<Activity> activities = checkIfCanDelete(groupBuyingService.searchActivities(activity));
        //分析时间
        for (Activity activity1 : activities) {
            if (activity1.getActivityStatus() == 0) {
                activities.remove(activity1);
            } else {
                Date nowTime = new Date();
                if (nowTime.getTime() - activity1.getGroupEndDate().getTime() > 0) {
                    //已经结束
                    activity1.setActivityStatus(1);
                } else if (nowTime.getTime() - activity1.getGroupStartDate().getTime() < 0) {
                    //还没开始
                    activity1.setActivityStatus(3);
                } else {
                    //进行中
                    activity1.setActivityStatus(2);
                }
            }
        }
        return activities;
    }

    /**
     * 根据活动ID搜索已经参加此团购活动的团队
     *
     * @param activity_id
     * @return
     */
    @RequestMapping(value = "/searchGroups/{activity_id}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Group> searchGroups(@PathVariable int activity_id) {
        System.out.println("activity_id:" + activity_id);
        return groupBuyingService.searchGroups(activity_id);
    }


    /**
     * 获得排序最高，价格最低的团购活动
     * @return
     */
    @RequestMapping(value = "/getRecommendedGroupBuying", method = RequestMethod.POST)
    @ResponseBody
    public Activity getRecommendedGroupBuying() {
        return groupBuyingService.getRecommendedGroupBuying();
    }

    /**
     * 开团操作
     *
     * @param activityID
     * @param leaderID
     * @param openDate
     * @param receiverName
     * @param receiverTEL
     * @param receiverPostcode
     * @param receiverAddress
     * @param billNeeded
     */
    @RequestMapping(value = "/initiateGroupBuying/{activityID}/{leaderID}/{openDate}/" +
            "{receiverName}/{receiverTEL}/{receiverPostcode}/{receiverAddress}/{billNeeded}", method = RequestMethod.POST)
    @ResponseBody
    public void initiateGroupBuying(@PathVariable int activityID, @PathVariable int leaderID,
                                    @PathVariable String openDate, @PathVariable String receiverName,
                                    @PathVariable String receiverTEL, @PathVariable int receiverPostcode,
                                    @PathVariable String receiverAddress, @PathVariable int billNeeded) {
//        System.out.println(activityID);
//        System.out.println(leaderID);
//        System.out.println(groupID);
//        System.out.println(openDate);
//        System.out.println(receiverName);
//        System.out.println(receiverTEL);
//        System.out.println(receiverPostcode);
//        System.out.println(receiverAddress);
//        System.out.println(billNeeded);

        //封装数据
        Group group = new Group();
        Activity activity = new Activity();
        activity.setActivityID(activityID);
        group.setActivity(activity);
        //人数初始化为1
        group.setCurrent_num(1);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(leaderID);
        group.setLeader(userInfo);
        //状态初始化为进行中，即为1
        group.setState(1);


        OpenGroupList openGroupList = new OpenGroupList();
        openGroupList.setLeader(userInfo);
        openGroupList.setBillNeeded(billNeeded);
        openGroupList.setReceiverAddress(receiverAddress);
        openGroupList.setReceiverName(receiverName);
        openGroupList.setReceiverPostCode(receiverPostcode);
        openGroupList.setReceiverTelephone(receiverTEL);


        Date groupOpenDate = null;
        if (openDate != null & !"".equals(openDate)) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                groupOpenDate = sdf.parse(openDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            openGroupList.setOpenDate(groupOpenDate);
        }

        groupBuyingService.initiateGroupBuying(group, openGroupList);
    }


    /**
     * 发布团购活动
     */
    @RequestMapping(value = "/releaseActivity/{productID}/{requiredNum}/{group_buying_price}/{groupStartDate}" +
            "/{groupEndDate}", method = RequestMethod.POST)
    @ResponseBody
    public void releaseActivity(@PathVariable int productID, @PathVariable int requiredNum, @PathVariable double group_buying_price,
                                @PathVariable String groupStartDate, @PathVariable String groupEndDate) {
        System.out.println(productID);
        System.out.println(requiredNum);
        System.out.println(group_buying_price);
        System.out.println(groupStartDate);
        System.out.println(groupEndDate);
        Activity activity = new Activity();
        Product product = new Product();
        product.setProduct_id(productID);
        activity.setProduct(product);
        activity.setRequiredNumber(requiredNum);
        activity.setGroup_buying_price(group_buying_price);
        Date startDate = null;
        Date endDate = null;
        //活动开始时间
        if (groupStartDate != null & !"".equals(groupStartDate)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse(groupStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activity.setGroupStartDate(startDate);
        }
        //活动结束时间
        if (groupEndDate != null & !"".equals(groupEndDate)) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                endDate = sdf.parse(groupEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activity.setGroupEndDate(endDate);
        }

        //判断当前时间和活动起止时间的关系
        Date nowTime = new Date();
        if (startDate != null && endDate != null) {
            if (nowTime.getTime() > startDate.getTime()) {
                if (nowTime.getTime() < endDate.getTime()) {
                    activity.setActivityStatus(2);
                } else {
                    activity.setActivityStatus(3);
                }
            } else {
                activity.setActivityStatus(1);
            }
        }
        groupBuyingService.releaseActivity(activity);
    }


    /**
     * 根据groupID查询团购队伍的相关信息
     *
     * @return
     */
    @RequestMapping(value = "/searchGroupInfo/{groupID}", method = RequestMethod.POST)
    @ResponseBody
    public Group searchGroupInfo(@PathVariable int groupID) {
        return groupBuyingService.searchGroupInfo(groupID);
    }


    /**
     * 根据activityID查询团购活动的相关信息
     *
     * @param activityID
     * @return
     */
    @RequestMapping(value = "/searchActivityInfo/{activityID}", method = RequestMethod.POST)
    @ResponseBody
    public Activity searchActivityInfo(@PathVariable int activityID) {
        return groupBuyingService.searchActivityInfo(activityID);
    }

    /**
     * 跟团
     *
     * @param joinerID
     * @param groupID
     * @param joinDate
     * @param receiverName
     * @param receiverTEL
     * @param receiverPostcode
     * @param receiverAddress
     * @param billNeeded
     */
    @RequestMapping(value = "/joinGroupBuying/{joinerID}/{groupID}/{joinDate}/" +
            "{receiverName}/{receiverTEL}/{receiverPostcode}/{receiverAddress}/{billNeeded}", method = RequestMethod.POST)
    @ResponseBody
    public void joinGroupBuying(@PathVariable int joinerID, @PathVariable int groupID,
                                @PathVariable String joinDate, @PathVariable String receiverName,
                                @PathVariable String receiverTEL, @PathVariable int receiverPostcode,
                                @PathVariable String receiverAddress, @PathVariable int billNeeded) {
        //封装数据
        JoinGroupList joinGroupList = new JoinGroupList();
        Group group = new Group();
        group.setGroupID(groupID);
        joinGroupList.setGroup(group);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(joinerID);
        joinGroupList.setJoiner(userInfo);
        joinGroupList.setReceiverName(receiverName);
        joinGroupList.setReceiverTelephone(receiverTEL);
        joinGroupList.setReceiverPostCode(receiverPostcode);
        joinGroupList.setReceiverAddress(receiverAddress);
        joinGroupList.setBillNeeded(billNeeded);

        Date groupJoinDate = null;
        if (joinDate != null & !"".equals(joinDate)) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                groupJoinDate = sdf.parse(joinDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            joinGroupList.setJoinDate(groupJoinDate);
        }
        groupBuyingService.joinGroupBuying(joinGroupList);
        //人数加一之后做个判断，看是否已经达到要求参与的人数
        if (checkIfEnough(groupID)) {
            //首先获得所有参与者
            ArrayList<Integer> userIDs = groupBuyingService.searchJoiners(groupID);

            //应该往此group中的每个user在user_message表中插入一条数据
            for (int userID : userIDs) {
                String messageTitle = "团购成功！";
                String messageBody = "您的团购成功了";
                messageService.addMessage(userID, messageTitle, messageBody);
            }
        }
    }

    /**
     * 检查是否已经达到要求参与的人数
     *
     * @param groupID
     * @return
     */
    public boolean checkIfEnough(int groupID) {
        return groupBuyingService.checkIfEnough(groupID);
    }

    /**
     * 检查是否可以删除，如果已经开始就不能删除并打个标记
     *
     * @param activities
     * @return
     */
    public ArrayList<Activity> checkIfCanDelete(ArrayList<Activity> activities) {
        return groupBuyingService.checkIfCanDelete(activities);
    }

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为3（被删除）
     *
     * @param a_id
     */
    public void deleteActivities(int[] a_id) {

    }

    /**
     * 修改团购信息
     * @param activityID
     * @param requiredNumber
     * @param group_buying_price
     */
    @RequestMapping (value = "/modifyActivityInfo/{activityID}/{requiredNumber}/{group_buying_price}")
    @ResponseBody
    public void modifyActivityInfo(@PathVariable int activityID,@PathVariable int requiredNumber,@PathVariable double group_buying_price){
        groupBuyingService.modifyActivityInfo(activityID,requiredNumber,group_buying_price);
    }

}
