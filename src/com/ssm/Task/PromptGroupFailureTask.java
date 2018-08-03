package com.ssm.Task;

import com.ssm.model.bean.Group;
import com.ssm.model.bean.JoinGroupList;
import com.ssm.model.bean.OpenGroupList;
import com.ssm.model.service.GroupBuyingService;
import com.ssm.model.service.MessageService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenyufeng on 2018/7/29.
 */

/**
 * <p>该类用于提示团购失败，在GroupBuyingController中的定时器到指定时间后被触发并运行其execute方法
 *    此时将构建团购失败的消息标题和消息体，将消息插入user_message表中，目标用户为所有参与此团购的用户，
 *    包括团长和所有参团者。
 * </p>
 *
 * @author chenyufeng
 */

@Component
public class PromptGroupFailureTask implements Job {
    @Autowired
    private GroupBuyingService groupBuyingService;
    @Autowired
    private MessageService messageService;

    public static PromptGroupFailureTask promptGroupFailureTask;

    @PostConstruct
    public void init() {
        promptGroupFailureTask = this;
    }

    /**
     * 在团购活动结束时此方法被调用
     * 方法中首先从参数map中获得groupID
     * 然后检查人数是否满足活动的要求人数，如果不满足，则提示用户团购失败，方法同提示团购成功
     * 提示的方法为利用messageService往user_message表中插入新的消息行
     * 之后交给消息提示模块显示以及后续操作
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //将会获得groupID
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        int groupID = dataMap.getInt("groupID");
        Group group1 = promptGroupFailureTask.groupBuyingService.searchGroupInfo(groupID);

        //检查人数是否满足活动的要求人数，如果不满足，则提示用户团购失败，方法同提示团购成功
        if (!promptGroupFailureTask.groupBuyingService.checkIfEnough(groupID)) {

            //应该往此group中的每个user在user_message表中插入一条数据
            //首先给团长发消息
            OpenGroupList openGroupList = promptGroupFailureTask.groupBuyingService.getOpenGroupList(groupID).get(0);
            String messageTitle = "您发起的团购已经失败";
            String bill = "不需要纸质发票";
            if (openGroupList.getBillNeeded() == 1) {
                bill = "需要纸质发票";
            }
            //messageBody显示具体的团购信息并有一个已经失败的标志就行,具体为要显示的html页的代码
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<div id=\"user-b\" class=\"od-box\">" +
                    "        <!--html5 nav-->" +
                    "        <nav class=\"j-nav navbar\">" +
                    "            <a href=\"javascript:history.back(-1)\"><img src=\"img/return.png\" style=\"width: 20px;height: inherit\"></a>" +
                    "            <span class=\"user-title\">消息详情</span>" +
                    "            <div class=\"shopping-cart fr\">" +
                    "            </div>" +
                    "        </nav>" +
                    "<div class=\"od-details\"><img src=\"/img/fail_group_buying.jpg\"></div>" +
                    "        <!-- 订单信息 -->" +
                    "        <div class=\"od-infor\">" +
                    "            <table cellpadding=\"1\" cellspacing=\"0\">" +
                    "                <tbody><tr>" +
                    "                    <td width=\"80px;\">团购状态:</td>" +
                    "                    <td class=\"od-red\">失败</td>" +
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
                    "                    <td>失败时间:</td>" +
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
                    "                <h2>" + group1.getActivity().getProduct().getProduct_info().substring(0, 20) + "</h2>" +
                    "                <div class=\"myorder-cost\">" +
                    "                    <span>原价" + group1.getActivity().getProduct().getOriginal_price() + "</span>" +
                    "                    <span class=\"mc-t\">团购价￥" + group1.getActivity().getGroup_buying_price() + "/件</span>" +
                    "                </div>" +
                    "            </div>" +
                    "        </div>" +
                    "    </div>");
            String messageBody = stringBuilder.toString();
            promptGroupFailureTask.messageService.addMessage(openGroupList.getLeader().getUser_id(), messageTitle, messageBody);


            //应该往此group中的每个user在user_message表中插入一条数据
            //接下来给团员们发消息
            ArrayList<JoinGroupList> joinGroupLists = promptGroupFailureTask.groupBuyingService.getJoinGroupList(groupID);

            for (JoinGroupList joinGroupList1 : joinGroupLists) {
                String messageTitle1 = "您参与的团购已经失败！";
                //messageBody显示具体的团购信息并有一个已失败的标志就行,具体为要显示的html页的代码
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
                        "        <!-- 订单信息 -->" +
                        "        <div class=\"od-infor\">" +
                        "            <table cellpadding=\"1\" cellspacing=\"0\">" +
                        "                <tbody><tr>" +
                        "                    <td width=\"80px;\">团购状态:</td>" +
                        "                    <td class=\"od-red\">已经失败</td>" +
                        "                </tr>" +
                        "                <tr>" +
                        "                    <td>团购价格:</td>" +
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
                        "                    <td>失败时间:</td>" +
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
                        "                <h2>" + group1.getActivity().getProduct().getProduct_info().substring(0, 20) + "</h2>" +
                        "                <div class=\"myorder-cost\">" +
                        "                    <span>原价" + group1.getActivity().getProduct().getOriginal_price() + "</span>" +
                        "                    <span class=\"mc-t\">团购价￥" + group1.getActivity().getGroup_buying_price() + "/件</span>" +
                        "                </div>" +
                        "            </div>" +
                        "        </div>" +
                        "    </div>");
                String messageBody1 = stringBuilder1.toString();
                promptGroupFailureTask.messageService.addMessage(joinGroupList1.getJoiner().getUser_id(), messageTitle1, messageBody1);
            }
        }
    }
}
