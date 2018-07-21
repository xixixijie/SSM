package com.ssm.controller;

import com.ssm.model.bean.*;
import com.ssm.model.service.GroupBuyingService;
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

    /**
     * 根据搜索条件搜索对应的活动集合
     * @return
     */
    @RequestMapping(value="/searchActivities/{productName}/{activityStatus}/{groupStartDate}/{groupEndDate}", method=RequestMethod.POST)
    @ResponseBody
    public ArrayList<Activity> searchActivities(@PathVariable String productName,@PathVariable int activityStatus,
                                                @PathVariable String groupStartDate,@PathVariable String groupEndDate
    ){
        System.out.println(productName);
        System.out.println(activityStatus);
        System.out.println(groupStartDate);
        System.out.println(groupEndDate);
        Activity activity = new Activity();
        Product product = new Product();
        product.setProduct_name("小米6");
        activity.setProduct(product);
        return groupBuyingService.searchActivities(activity);
    }

    /**
     * 根据活动ID搜索已经参加此团购活动的团队
     * @param activity_id
     * @return
     */
    @RequestMapping(value="/searchGroups/{activity_id}", method=RequestMethod.POST)
    @ResponseBody
    public ArrayList<Group> searchGroups(@PathVariable int activity_id){
        System.out.println("activity_id:"+activity_id);
        return groupBuyingService.searchGroups(activity_id);
    }

    //开团操作
    @RequestMapping(value="/initiateGroupBuying/{activityID}/{leaderID}/{groupID}/{openDate}/" +
            "{receiverName}/{receiverTEL}/{receiverPostcode}/{receiverAddress}/{billNeeded}", method=RequestMethod.POST)
    @ResponseBody
    public void initiateGroupBuying(@PathVariable int activityID,@PathVariable int leaderID,
                                    @PathVariable String openDate, @PathVariable String receiverName,
                                    @PathVariable String receiverTEL, @PathVariable int receiverPostcode,
                                    @PathVariable String receiverAddress, @PathVariable int billNeeded){
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
        group.setLeader(userInfo);
        //状态初始化为进行中，即为1
        group.setState(1);


        OpenGroupList openGroupList = new OpenGroupList();
        openGroupList.setBillNeeded(billNeeded);
        openGroupList.setReceiverAddress(receiverAddress);
        openGroupList.setReceiverName(receiverName);
        openGroupList.setReceiverPostCode(receiverPostcode);
        openGroupList.setReceiverTelephone(receiverTEL);


        Date groupOpenDate = null;
        if(openDate!=null&!"".equals(openDate)){

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                groupOpenDate = sdf.parse(openDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            openGroupList.setOpenDate(groupOpenDate);
        }

        groupBuyingService.initiateGroupBuying(group,openGroupList);
    }


    /**
     * 发布团购活动
     */
    @RequestMapping(value="releaseActivity/{productID}/{requiredNum}/{group_buying_price}/{groupStartDate}" +
            "/{groupEndDate}", method=RequestMethod.POST)
    @ResponseBody
    public void releaseActivity(@PathVariable int productID,@PathVariable int requiredNum,@PathVariable double group_buying_price,
                                @PathVariable String groupStartDate,@PathVariable String groupEndDate){
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
        if(groupStartDate!=null&!"".equals(groupStartDate)){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse(groupStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activity.setGroupStartDate(startDate);
        }
        //活动结束时间
        if(groupEndDate!=null&!"".equals(groupEndDate)){

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
        if(startDate!=null&&endDate!=null){
            if(nowTime.getTime()>startDate.getTime()){
                if (nowTime.getTime()<endDate.getTime()){
                    activity.setActivityStatus(2);
                }else {
                    activity.setActivityStatus(3);
                }
            }else {
                activity.setActivityStatus(1);
            }
        }
        groupBuyingService.releaseActivity(activity);
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
