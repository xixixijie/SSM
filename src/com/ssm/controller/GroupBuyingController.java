package com.ssm.controller;

import com.ssm.Task.PromptGroupFailureTask;
import com.ssm.model.bean.*;
import com.ssm.model.service.GroupBuyingService;
import com.ssm.model.service.MessageService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
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
import java.util.HashSet;
import java.util.Iterator;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 * <p>
 * 此类是管理团购模块相关事务的控制类，主要涉及的功能为
 * 1.根据搜索条件搜索对应的活动集合
 * 2.分页版本的根据搜索条件搜索对应的活动集合
 * 3.根据活动ID搜索已经参加此团购活动的团队
 * 4.获得价格较低且与用户历史购买兴趣最吻合的团购活动
 * 5.开团操作
 * 6.发布团购活动
 * 7.根据groupID查询团购队伍的相关信息
 * 8.根据activityID查询团购活动的相关信息
 * 9.跟团操作
 * 10.检查是否已经达到要求参与的人数
 * 11.检查是否可以删除
 * 12.根据活动ID批量删除团购活动
 * 13.修改团购信息
 * 14.根据gourpID查询开团记录
 * 15.根据groupID查询跟团记录
 * 16.根据userID查询地址集合
 * 17.根据AddressID查询具体的address信息
 * 18.根据userID查询团购信息
 * 19.根据userID查询参团信息
 * 20.获得刚开团的groupID
 * </p>
 *
 * @author chenyufeng
 * @see GroupBuyingService 在本类中主要用于团购活动搜索，
 * 团购活动发布，团购活动删除，团购信息修改，获得被推荐的团购活动,跟团，参团等操作
 * @see MessageService 在本类中主要用于向数据库中插入团购相关的消息，
 * 读取团购相关的消息，标记团购相关的消息为已读
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
     * @param productName  搜索条件-商品名称   支持模糊查询
     * @param groupStartDate   搜索条件-开始时间
     * @param groupEndDate    搜索条件-结束时间
     * @return  返回满足搜索条件的活动集合，其中商品名称支持模糊查询，若搜索条件为空则返回所有结果
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
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity1 = iterator.next();
            if (activity1.getActivityStatus() == 0) {
                iterator.remove();
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
     * 根据搜索条件搜索对应的活动集合分页版本
     *
     * @param productName   搜索条件-商品名称   支持模糊查询
     * @param groupStartDate  搜索条件-开始时间
     * @param groupEndDate   搜索条件-结束时间
     * @param pageNum  页数，用于分页显示
     * @return  返回满足搜索条件的活动集合，其中商品名称支持模糊查询，若搜索条件为空则返回所有结果
     */
    @RequestMapping(value = "/searchActivities/{productName}/{groupStartDate}/{groupEndDate}/{pageNum}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Activity> searchActivities(@PathVariable String productName,
                                                @PathVariable String groupStartDate, @PathVariable String groupEndDate, @PathVariable int pageNum
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
        ArrayList<Activity> activities = checkIfCanDelete(groupBuyingService.searchActivities(activity, pageNum));
        //分析时间
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity1 = iterator.next();
            if (activity1.getActivityStatus() == 0) {
                iterator.remove();
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
     * @param activity_id  活动ID，用于唯一区分团购活动
     * @return   返回已经参加此团购活动的团队的集合
     */
    @RequestMapping(value = "/searchGroups/{activity_id}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Group> searchGroups(@PathVariable int activity_id) {
        System.out.println("activity_id:" + activity_id);
        return groupBuyingService.searchGroups(activity_id);
    }


    /**
     * 获得价格较低且与用户历史购买兴趣最吻合的团购活动
     * 若用户没有参团记录则返回团购价格最低的团购活动
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return  返回价格较低且与用户历史购买兴趣最吻合的团购活动，若用户没有参团记录则返回团购价格最低的团购活动
     */
    @RequestMapping(value = "/getRecommendedGroupBuying/{userID}", method = RequestMethod.POST)
    @ResponseBody
    public Activity getRecommendedGroupBuying(@PathVariable int userID) throws InterruptedException {
        //首先获得该用户参与过的所有Activity的Product

        ArrayList<Group> groupList = groupBuyingService.searchGroupsByUserID(userID);
        ArrayList<JoinGroupList> joinGroupLists = groupBuyingService.searchJoinGroupListByUserID(userID);

        HashSet<Product> relevantProducts = new HashSet<Product>();

        for (Iterator<Group> groupIterator = groupList.iterator(); groupIterator.hasNext(); ) {
            Group group = groupIterator.next();
            relevantProducts.add(group.getActivity().getProduct());
        }
        for (Iterator<JoinGroupList> joinGroupListIterator = joinGroupLists.iterator(); joinGroupListIterator.hasNext(); ) {
            JoinGroupList joinGroupList = joinGroupListIterator.next();
            relevantProducts.add(joinGroupList.getGroup().getActivity().getProduct());
        }

        //接下来获得团购价格最低的五个Activity
        ArrayList<Activity> recommendActivities = groupBuyingService.getRecommendedGroupBuying();
        double arr[];
        arr = new double[recommendActivities.size()];
        //循环与relevantProducts中的product的name做相似度分析
        int j = 0;
        for (Iterator<Activity> activityIterator = recommendActivities.iterator(); activityIterator.hasNext(); ) {
            Activity activity = activityIterator.next();
            for (Iterator<Product> productIterator = relevantProducts.iterator(); productIterator.hasNext(); ) {
                Product product = productIterator.next();
                double similarDegree = groupBuyingService.getSimilarDegree(activity.getProduct().getProduct_name(), product.getProduct_name());
                Thread.sleep(100);
                arr[j] += similarDegree;
            }
            j++;
        }
        //选出相似度最高的Activity的下标
        double flag = arr[0];
        int first=0;
        for (int i=0;i<recommendActivities.size();i++){
            if(arr[i]>flag){
                first = i;
                flag = arr[i];
            }
        }
        //获得相似度最高的Activity返回给前端显示
        return recommendActivities.get(first);
    }

    /**
     * 开团操作
     *
     * @param activityID  活动ID，用于唯一区分团购活动
     * @param leaderID     开团者的userID，用于唯一区分用户
     * @param openDate     开团日期
     * @param receiverName    收货地址中收货人姓名
     * @param receiverTEL     收货地址中收货人联系电话
     * @param receiverPostcode    收货地址中邮编
     * @param receiverAddress     具体的收获地址
     * @param billNeeded        是否需要纸质发票  0不需要  1需要
     */
    @RequestMapping(value = "/initiateGroupBuying/{activityID}/{leaderID}/{openDate}/" +
            "{receiverName}/{receiverTEL}/{receiverPostcode}/{receiverAddress}/{billNeeded}", method = RequestMethod.POST)
    @ResponseBody
    public void initiateGroupBuying(@PathVariable int activityID, @PathVariable int leaderID,
                                    @PathVariable String openDate, @PathVariable String receiverName,
                                    @PathVariable String receiverTEL, @PathVariable int receiverPostcode,
                                    @PathVariable String receiverAddress, @PathVariable int billNeeded) {
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


        int groupID = groupBuyingService.getGroupID();
        String jobIdentity = "job" + groupID;
        //根据acvitityID查到结束时间作为定时任务实行的时间
        Date activityEndDate = groupBuyingService.searchActivityInfo(activityID).getGroupEndDate();

        //获得刚插入的groupID并添加定时器
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail jobDetail = newJob(PromptGroupFailureTask.class)
                    .withIdentity(jobIdentity, "group1")
                    .usingJobData("groupID", groupID)
                    .build();

            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(activityEndDate)
                    .forJob(jobIdentity, "group1")
                    .withSchedule(
                            simpleSchedule().withMisfireHandlingInstructionFireNow()
                    )
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

//            Thread.sleep(60000);
//            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 发布团购活动
     * @param productID  商品ID，用于唯一区别商品
     * @param requiredNum   团购活动需要的人数
     * @param group_buying_price     商品的团购价
     * @param groupStartDate      团购活动开始日期
     * @param groupEndDate        团购活动结束日期
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
     * @param groupID   团队ID，用于唯一区别团购队伍
     * @return   返回根据groupID查询团购队伍的相关信息，封装在Group对象中
     */
    @RequestMapping(value = "/searchGroupInfo/{groupID}", method = RequestMethod.POST)
    @ResponseBody
    public Group searchGroupInfo(@PathVariable int groupID) {
        return groupBuyingService.searchGroupInfo(groupID);
    }


    /**
     * 根据activityID查询团购活动的相关信息
     *
     * @param activityID  团购活动ID，用于唯一区别团购活动
     * @return  返回根据activityID查询团购活动的相关信息，封装在Activity对象中
     */
    @RequestMapping(value = "/searchActivityInfo/{activityID}", method = RequestMethod.POST)
    @ResponseBody
    public Activity searchActivityInfo(@PathVariable int activityID) {
        return groupBuyingService.searchActivityInfo(activityID);
    }

    /**
     * 跟团操作
     * 主要的操作为生成跟团记录，并把团队的当前人数加一
     *
     * @param joinerID   跟团者ID，用于唯一区别跟团者
     * @param groupID    团队ID，用于唯一区别团购团队
     * @param joinDate   跟团日期
     * @param receiverName   收货地址中的收货人姓名
     * @param receiverTEL     收货地址中收货人联系电话
     * @param receiverPostcode    收货地址中邮编
     * @param receiverAddress     具体的收获地址
     * @param billNeeded        是否需要纸质发票  0不需要  1需要
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
            Group group1 = groupBuyingService.searchGroupInfo(groupID);

            //应该往此group中的每个user在user_message表中插入一条数据
            //首先给团长发消息
            OpenGroupList openGroupList = groupBuyingService.getOpenGroupList(groupID).get(0);
            String messageTitle = "您发起的团购成功了！";
            String bill = "不需要纸质发票";
            if (openGroupList.getBillNeeded() == 1) {
                bill = "需要纸质发票";
            }
            //messageBody显示具体的团购信息并有一个已成功的标志就行,具体为要显示的html页的代码
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<div id=\"user-b\" class=\"od-box\">" +
                    "        <!--html5 nav-->" +
                    "        <nav class=\"j-nav navbar\">" +
                    "            <a href=\"javascript:history.back(-1)\"><img src=\"img/return.png\" style=\"width: 20px;height: inherit\"></a>" +
                    "            <span class=\"user-title\">消息详情</span>" +
                    "            <div class=\"shopping-cart fr\">" +
                    "            </div>" +
                    "        </nav>" +
                    "        <div class=\"od-details\">" +
                    "            <!-- 订单进度，生效追加样式“-cur”即可 -->" +
                    "            <div class=\"od-express\">" +
                    "                <ul>" +
                    "                    <li class=\"place place-cur\">" +
                    "                        <img src=\"img/tijiaodingdan.png\" style=\"width: 30px;height: 30px;\">" +
                    "                    </li>" +
                    "                    <li class=\"delivery\">" +
                    "                        <img src=\"img/peisongzhong.png\" style=\"width: 30px;height: 30px;\">" +
                    "                    </li>" +
                    "                    <li class=\"sign\">" +
                    "                        <img src=\"img/qianshouchenggong.png\" style=\"width: 30px;height: 30px;\">" +
                    "                    </li>" +
                    "                </ul>" +
                    "            </div>" +
                    "        </div>" +
                    "        <!-- 订单信息 -->" +
                    "        <div class=\"od-infor\">" +
                    "            <table cellpadding=\"1\" cellspacing=\"0\">" +
                    "                <tbody><tr>" +
                    "                    <td width=\"80px;\">团购状态:</td>" +
                    "                    <td class=\"od-red\">已成功</td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>团购价格:</td>" +
                    "                    <td class=\"od-red\">" + group1.getActivity().getGroup_buying_price() + " </td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>送至:</td>" +
                    "                    <td>" + openGroupList.getReceiverAddress() + "</td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>收货人:</td>" +
                    "                    <td>" + openGroupList.getReceiverName() + " " + openGroupList.getReceiverTelephone() + "</td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>团购编号:</td>" +
                    "                    <td>" + group1.getGroupID() + "</td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>生效时间:</td>" +
                    "                    <td>" + new Date().toString() + "</td>" +
                    "                </tr>" +
                    "                <tr>" +
                    "                    <td>发票:</td>" +
                    "                    <td>" + bill + "</td>" +
                    "                </tr>" +
                    "                </tbody></table>" +
                    "        </div>\n" +
                    "        <!-- 商品详情 -->" +
                    "        <h4>商品详情</h4>" +
                    "        <div class=\"mc-sum-box\">" +
                    "            <div class=\"myorder-sum fl\"><img src=\"/img/" + group1.getActivity().getProduct().getCover_url() + "\"></div>" +
                    "            <div class=\"myorder-text\">" +
                    "                <h1>" + group1.getActivity().getProduct().getProduct_name() + "</h1>" +
                    "                <h2>" + group1.getActivity().getProduct().getProduct_info().substring(0,20) + "</h2>" +
                    "                <div class=\"myorder-cost\">" +
                    "                    <span>原价" + group1.getActivity().getProduct().getOriginal_price() + "</span>" +
                    "                    <span class=\"mc-t\">团购价￥" + group1.getActivity().getGroup_buying_price() + "/件</span>" +
                    "                </div>" +
                    "            </div>" +
                    "        </div>" +
                    "    </div>");
            String messageBody = stringBuilder.toString();
            messageService.addMessage(openGroupList.getLeader().getUser_id(), messageTitle, messageBody);


            //应该往此group中的每个user在user_message表中插入一条数据
            //接下来给团员们发消息
            ArrayList<JoinGroupList> joinGroupLists = groupBuyingService.getJoinGroupList(groupID);

            for (JoinGroupList joinGroupList1 : joinGroupLists) {
                String messageTitle1 = "您参与的团购成功！";
                //messageBody显示具体的团购信息并有一个已成功的标志就行,具体为要显示的html页的代码
                String bill1 = "不需要纸质发票";
                if (joinGroupList1.getBillNeeded() == 1) {
                    bill1 = "需要纸质发票";
                }
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("<div id=\"user-b\" class=\"od-box\">" +
                        "        <!--html5 nav-->" +
                        "        <nav class=\"j-nav navbar\">" +
                        "            <a href=\"javascript:history.back(-1)\"><img src=\"img/return.png\" style=\"width: 20px;height: inherit\"></a>" +
                        "            <span class=\"user-title\">消息详情</span>" +
                        "            <div class=\"shopping-cart fr\">" +
                        "            </div>" +
                        "        </nav>" +
                        "        <div class=\"od-details\">" +
                        "            <!-- 订单进度，生效追加样式“-cur”即可 -->" +
                        "            <div class=\"od-express\">" +
                        "                <ul>" +
                        "                    <li class=\"place place-cur\">" +
                        "                        <img src=\"img/tijiaodingdan.png\" style=\"width: 30px;height: 30px;\">" +
                        "                    </li>" +
                        "                    <li class=\"delivery\">" +
                        "                        <img src=\"img/peisongzhong.png\" style=\"width: 30px;height: 30px;\">" +
                        "                    </li>" +
                        "                    <li class=\"sign\">" +
                        "                        <img src=\"img/qianshouchenggong.png\" style=\"width: 30px;height: 30px;\">" +
                        "                    </li>" +
                        "                </ul>" +
                        "            </div>" +
                        "        </div>" +
                        "        <!-- 订单信息 -->" +
                        "        <div class=\"od-infor\">" +
                        "            <table cellpadding=\"1\" cellspacing=\"0\">" +
                        "                <tbody><tr>" +
                        "                    <td width=\"80px;\">团购状态:</td>" +
                        "                    <td class=\"od-red\">已成功</td>" +
                        "                </tr>" +
                        "                <tr>" +
                        "                    <td>团购价格:</td" +
                        "                    <td class=\"od-red\">" + group1.getActivity().getGroup_buying_price() + " </td>" +
                        "                </tr>" +
                        "                <tr>" +
                        "                    <td>送至:</td>" +
                        "                    <td>" + joinGroupList1.getReceiverAddress() + "</td>" +
                        "                </tr>" +
                        "                <tr>" +
                        "                    <td>收货人:</td>" +
                        "                    <td>" + joinGroupList1.getReceiverName() + " " + joinGroupList1.getReceiverTelephone() + "</td>" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td>团购编号:</td>" +
                        "                    <td>" + group1.getGroupID() + "</td>" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td>生效时间:</td>" +
                        "                    <td>" + new Date().toString() + "</td>" +
                        "                </tr>" +
                        "                <tr>" +
                        "                    <td>发票:</td>" +
                        "                    <td>" + bill1 + "</td>" +
                        "                </tr>" +
                        "                </tbody></table>" +
                        "        </div>\n" +
                        "        <!-- 商品详情 -->" +
                        "        <h4>商品详情</h4>" +
                        "        <div class=\"mc-sum-box\">" +
                        "            <div class=\"myorder-sum fl\"><img src=\"/img/" + group1.getActivity().getProduct().getCover_url() + "\"></div>" +
                        "            <div class=\"myorder-text\">" +
                        "                <h1>" + group1.getActivity().getProduct().getProduct_name() + "</h1>" +
                        "                <h2>" + group1.getActivity().getProduct().getProduct_info().substring(0,20) + "</h2>" +
                        "                <div class=\"myorder-cost\">" +
                        "                    <span>原价" + group1.getActivity().getProduct().getOriginal_price() + "</span>" +
                        "                    <span class=\"mc-t\">团购价￥" + group1.getActivity().getGroup_buying_price() + "/件</span>" +
                        "                </div>" +
                        "            </div>" +
                        "        </div>" +
                        "    </div>");
                String messageBody1 = stringBuilder1.toString();
                messageService.addMessage(joinGroupList1.getJoiner().getUser_id(), messageTitle1, messageBody1);
            }
        }
    }

    /**
     * 检查是否已经达到要求参与的人数
     *
     * @param groupID  团队ID，用于唯一区别团购团队
     * @return  true表示已经达到要求参与的人数 false表示尚未达到要求参与的人数
     */
    public boolean checkIfEnough(int groupID) {
        return groupBuyingService.checkIfEnough(groupID);
    }

    /**
     * 检查是否可以删除，如果已经开始就不能删除并打个标记
     * 如果团购活动已经开始是不能被删除的，其canDelete属性将被置为false，
     * 反之如果尚未开始，则其canDelete属性将被置为true
     *
     * @param activities canDelete属性没有被赋值的活动集合
     * @return  canDelete根据是否开始被正确赋值的活动集合
     */
    public ArrayList<Activity> checkIfCanDelete(ArrayList<Activity> activities) {
        return groupBuyingService.checkIfCanDelete(activities);
    }

    /**
     * 根据活动ID批量删除团购活动
     * 具体做法为将团购活动的状态置为0（被删除）
     *
     * @param checkID 被选中的需要被删除的团购活动的ID的数组
     */
    @RequestMapping(value = "/deleteActivities/{checkID}")
    @ResponseBody
    public void deleteActivities(@PathVariable int[] checkID) {
        groupBuyingService.deleteActivities(checkID);

    }

    /**
     * 修改团购信息
     *
     * @param activityID  被修改的团购活动的ID
     * @param requiredNumber  修改后的团购活动需要人数
     * @param group_buying_price   修改后的团购价格
     */
    @RequestMapping(value = "/modifyActivityInfo/{activityID}/{requiredNumber}/{group_buying_price}")
    @ResponseBody
    public void modifyActivityInfo(@PathVariable int activityID, @PathVariable int requiredNumber, @PathVariable double group_buying_price) {
        groupBuyingService.modifyActivityInfo(activityID, requiredNumber, group_buying_price);
    }


    /**
     * 根据gourpID查询开团记录
     *
     * @param groupID  团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有开团记录的集合
     */
    public ArrayList<OpenGroupList> getOpenGroupList(int groupID) {
        return groupBuyingService.getOpenGroupList(groupID);
    }


    /**
     * 根据groupID查询跟团记录
     *
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有跟团记录的集合
     */
    public ArrayList<JoinGroupList> getJoinGroupList(int groupID) {
        return groupBuyingService.getJoinGroupList(groupID);
    }


    /**
     * 根据userID查询地址集合
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return 该用户下所有的地址集合
     */
    @RequestMapping(value = "/getAddresses/{userID}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Address> getAddress(@PathVariable int userID) {
        return groupBuyingService.getAddress(userID);
    }


    /**
     * 根据AddressID查询具体的address信息
     *
     * @param addressID 地址ID，用于唯一区分地址
     * @return  该地址ID对应的具体的地址信息
     */
    @RequestMapping(value = "/searchAddressInfo/{addressID}", method = RequestMethod.POST)
    @ResponseBody
    public Address searchAddressInfo(@PathVariable int addressID) {
        return groupBuyingService.searchAddressInfo(addressID);

    }


    /**
     * 根据userID查询团购信息
     * @param userID 用户ID，用于唯一区分用户
     * @return 返回该用户的所有团购队伍记录的集合
     */
    @RequestMapping(value = "/searchGroupsByUserID/{userID}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Group> searchGroupsByUserID(@PathVariable int userID) {
        return groupBuyingService.searchGroupsByUserID(userID);

    }


    /**
     * 根据userID查询参团信息
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return 返回该用户的所有跟团记录的集合
     */
    @RequestMapping(value = "/searchJoinGroupListByUserID/{userID}", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<JoinGroupList> searchJoinGroupListByUserID(@PathVariable int userID) {
        return groupBuyingService.searchJoinGroupListByUserID(userID);
    }


    /**
     * 获得刚开团的groupID
     *
     * @return 返回刚生成的团购队伍的队伍ID
     */
    @RequestMapping(value = "/getNewGroupID", method = RequestMethod.POST)
    @ResponseBody
    public int getNewGroupID() {
        return groupBuyingService.getGroupID();
    }
}
