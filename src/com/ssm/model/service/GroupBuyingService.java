package com.ssm.model.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssm.model.bean.*;
import com.ssm.model.dao.GroupBuyingDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/19.
 */


/**
 * <p>
 * 团购活动模块的Service层
 * 涉及的主要操作为
 * 1.根据查询条件查询符合条件的所有团购活动
 * 2.根据团购活动号查询参与此团购活动的队伍
 * 3.开团操作
 * 4.获得最新创建的group的ID
 * 5.发布团购活动
 * 6.跟团操作
 * 7.通过运用JSON连接百度NLP的短文本相似度分析接口比较两个字符串的相似程度
 * 8.根据查询条件查询符合条件的所有团购活动分页版
 * 9.检查是否已经达到要求参与的人数
 * 10.检查团购活动是否可以删除
 * 11.根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为0（被删除）
 * 12.根据groupID查询joiner
 * 13.根据groupID查询leader
 * 14.根据groupID查询团购队伍的相关信息
 * 15.根据activityID查询团购活动的相关信息
 * 16.获得价格最低的五个团购活动
 * 17.修改团购信息
 * 18.根据gourpID查询开团记录
 * 19.根据groupID查询跟团记录
 * 20.根据userID查询地址集合
 * 21.根据AddressID查询具体的address信息
 * 22.据userID查询团购信息
 * 23.根据userID查询参团信息
 * 24.参团后当前人数+1
 * </p>
 *
 * @see GroupBuyingDAO 团购模块的DAO层
 */


@Service
public class GroupBuyingService {

    @Autowired
    private GroupBuyingDAO groupBuyingDAO;

    /**
     * 根据查询条件查询符合条件的所有团购活动
     *
     * @param activity 查询条件封装在Activity对象中
     * @return 返回满足搜索条件的活动集合
     */
    public ArrayList<Activity> searchActivities(Activity activity) {
        return groupBuyingDAO.searchActivities(activity);
    }

    /**
     * 根据团购活动号查询参与此团购活动的队伍
     *
     * @param activityID 活动ID，用于唯一区分团购活动
     * @return 返回已经参加此团购活动的团队的集合
     */
    public ArrayList<Group> searchGroups(int activityID) {
        return groupBuyingDAO.searchGroups(activityID);
    }

    /**
     * 开团操作
     *
     * @param group 开团操作需要的属性值封装在Group对象中
     */
    public void initiateGroupBuying(Group group, OpenGroupList openGroupList) {
        groupBuyingDAO.initiateGroupBuying(group);
        int groupID = groupBuyingDAO.getGroupID();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("OpenGroupList", openGroupList);
        map.put("groupID", groupID);
        groupBuyingDAO.initiateGroupBuyingSuffix(map);
    }

    /**
     * 发布团购活动
     *
     * @param activity 发布团购活动需要的属性值封装在Activity对象中
     */
    public void releaseActivity(Activity activity) {
        groupBuyingDAO.releaseActivity(activity);

    }

    /**
     * 跟团操作
     * 主要的操作为生成跟团记录，并把团队的当前人数加一
     *
     * @param joinGroupList 跟团操作需要的属性值封装在JoinGroupList对象中
     */
    public void joinGroupBuying(JoinGroupList joinGroupList) {
        groupBuyingDAO.updateCurrentNumber(joinGroupList);
        groupBuyingDAO.joinGroupBuying(joinGroupList);
    }

    /**
     * 检查是否已经达到要求参与的人数
     *
     * @param groupID 团队ID，用于唯一区别团购团队
     * @return true表示已经达到要求参与的人数 false表示尚未达到要求参与的人数
     */
    public boolean checkIfEnough(int groupID) {
        int nowNum = groupBuyingDAO.checkIfEnough(groupID);
        int requiredNum = groupBuyingDAO.getRequiredNumber(groupID);
        return nowNum == requiredNum;
    }

    /**
     * 检查是否可以删除，如果已经开始就不能删除并打个标记
     * 如果团购活动已经开始是不能被删除的，其canDelete属性将被置为false，
     * 反之如果尚未开始，则其canDelete属性将被置为true
     *
     * @param activities canDelete属性没有被赋值的活动集合
     * @return canDelete根据是否开始被正确赋值的活动集合
     */
    public ArrayList<Activity> checkIfCanDelete(ArrayList<Activity> activities) {
        for (Activity activity : activities) {
            Date groupStartDate = groupBuyingDAO.checkIfCanDelete(activity);
            Date nowDate = new Date();
            if (nowDate.getTime() - groupStartDate.getTime() > 0) {
                //尚未开始
                activity.setCanDelete(true);
            } else {
                //已经开始
                activity.setCanDelete(false);
            }
        }
        return activities;
    }

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为0（被删除）
     *
     * @param checkID 被选中的需要被删除的团购活动的ID的数组
     */
    public void deleteActivities(int[] checkID) {
        groupBuyingDAO.deleteActivities(checkID);
    }

    /**
     * 根据groupID取得所有的参与的UserID
     *
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有参与者的userID的集合包括开团者
     */
    public ArrayList<Integer> searchJoiners(int groupID) {
        ArrayList<Integer> userIDs = groupBuyingDAO.searchJoiners(groupID);
        userIDs.add(groupBuyingDAO.searchLeader(groupID));
        return userIDs;
    }

    /**
     * 根据groupID查询团购队伍的相关信息
     *
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 此groupID对应的团购队伍的相关信息封装在Group对象中
     */
    public Group searchGroupInfo(int groupID) {
        return groupBuyingDAO.searchGroupInfo(groupID);
    }

    /**
     * 根据activityID查询团购活动的相关信息
     *
     * @param activityID 团购活动ID，用于唯一区别团购活动
     * @return 返回根据activityID查询团购活动的相关信息，封装在Activity对象中
     */
    public Activity searchActivityInfo(int activityID) {
        return groupBuyingDAO.searchActivityInfo(activityID);
    }


    /**
     * 获得价格最低的五个团购活动
     * 截取DAO层返回的按团购价格升序排列的集合的子集合，取前五个元素
     *
     * @return 截取DAO层返回的按团购价格升序排列的集合的子集合，取前五个元素
     */
    public ArrayList<Activity> getRecommendedGroupBuying() {
        return new ArrayList<Activity>(groupBuyingDAO.getRecommendedGroupBuying().subList(0, 5));
    }

    /**
     * 修改团购信息
     *
     * @param activityID         被修改的团购活动的ID
     * @param requiredNumber     修改后的团购活动需要人数
     * @param group_buying_price 修改后的团购价格
     */
    public void modifyActivityInfo(int activityID, int requiredNumber, double group_buying_price) {
        Activity activity = new Activity();
        activity.setActivityID(activityID);
        activity.setRequiredNumber(requiredNumber);
        activity.setGroup_buying_price(group_buying_price);
        groupBuyingDAO.modifyActivityInfo(activity);
    }


    /**
     * 根据gourpID查询开团记录
     *
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有开团记录的集合
     */
    public ArrayList<OpenGroupList> getOpenGroupList(int groupID) {
        return groupBuyingDAO.getOpenGroupList(groupID);
    }

    /**
     * 根据groupID查询跟团记录
     *
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有跟团记录的集合
     */
    public ArrayList<JoinGroupList> getJoinGroupList(int groupID) {
        return groupBuyingDAO.getJoinGroupList(groupID);
    }

    /**
     * 根据查询条件查询符合条件的所有团购活动分页版
     *
     * @param activity 查询条件封装在Activity对象中
     * @param pageNum  分页用，当前页数
     * @return 根据查询条件查询符合条件的当前页数对应的集合
     */
    public ArrayList<Activity> searchActivities(Activity activity, int pageNum) {
        Page<Activity> page = PageHelper.startPage(pageNum, 5);
        groupBuyingDAO.searchActivities(activity);
        return new ArrayList<Activity>(page.getResult());
    }

    /**
     * 根据userID查询地址集合
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return 该用户下所有的地址集合
     */
    public ArrayList<Address> getAddress(int userID) {
        return groupBuyingDAO.getAddress(userID);
    }

    /**
     * 根据AddressID查询具体的address信息
     *
     * @param addressID 地址ID，用于唯一区分地址
     * @return 该地址ID对应的具体的地址信息
     */
    public Address searchAddressInfo(int addressID) {
        return groupBuyingDAO.searchAddressInfo(addressID);
    }

    /**
     * 据userID查询团购信息
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return 返回该用户的所有团购队伍记录的集合
     */
    public ArrayList<Group> searchGroupsByUserID(int userID) {
        return groupBuyingDAO.searchGroupsByUserID(userID);
    }

    /**
     * 根据userID查询参团信息
     *
     * @param userID 用户ID，用于唯一区分用户
     * @return 返回该用户的所有跟团记录的集合
     */
    public ArrayList<JoinGroupList> searchJoinGroupListByUserID(int userID) {
        return groupBuyingDAO.searchJoinGroupListByUserID(userID);
    }


    /**
     * 获得最近生成的Group的groupID
     *
     * @return 返回刚生成的团购队伍的队伍ID
     */
    public int getGroupID() {
        return groupBuyingDAO.getGroupID();
    }


    /**
     * 通过运用JSON连接百度NLP的短文本相似度分析接口比较两个字符串的相似程度
     *
     * @param text1 做比较的两个短文本的第一个
     * @param text2 做比较的两个短文本的第二个
     * @return 相似程度的量化结果
     */
    public double getSimilarDegree(String text1, String text2) {
        String resp = null;
        double score = 0.00;
        JSONObject obj = new JSONObject();
        try {
            obj.put("text_1", toGBK(text1));
            obj.put("text_2", toGBK(text2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String query = obj.toString();
        try {
            URL url = new URL("https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet?access_token=24.c627840888a67ecddfae7fce2de68d63.2592000.1535608573.282335-11612502"); //url地址

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            try (OutputStream os = connection.getOutputStream()) {
                os.write(query.getBytes("UTF-8"));
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String lines;
                StringBuffer sbf = new StringBuffer();
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sbf.append(lines);
                }
                System.out.println("返回来的报文：" + sbf.toString());
                resp = sbf.toString();
                JsonParser parse = new JsonParser();  //创建json解析器

                JsonObject json = (JsonObject) parse.parse(resp);  //创建jsonObject对象
                System.out.println("result:" + json.get("score").getAsDouble());  //将json数据转为为int型的数据
                score = json.get("score").getAsDouble();
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }
    public  String toGBK(String source) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = source.getBytes("GBK");
        for (byte b : bytes) {
            sb.append("%" + Integer.toHexString((b & 0xff)).toUpperCase());
        }
        return sb.toString();
    }

}




