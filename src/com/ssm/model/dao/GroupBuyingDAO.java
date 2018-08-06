package com.ssm.model.dao;

import com.ssm.model.bean.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/19.
 */

/**
 * <p>
 *     团购活动管理的DAO层
 *     涉及的主要操作为
 *     1.根据查询条件查询符合条件的所有团购活动
 *     2.根据团购活动号查询参与此团购活动的队伍
 *     3.开团操作
 *     4.获得最新创建的group的ID
 *     5.开团附属操作，生成开团记录
 *     6.发布团购活动
 *     7.跟团
 *     8.返回当前团队中的人数
 *     9.返回活动预先设定的需要的参与人数
 *     10.返回团购活动的开始时间
 *     11.根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为0（被删除）
 *     12.根据groupID查询joiner
 *     13.根据groupID查询leader
 *     14.根据groupID查询团购队伍的相关信息
 *     15.根据activityID查询团购活动的相关信息
 *     16.将团购活动按照团购价格升序，返回升序集合
 *     17.修改团购信息
 *     18.根据gourpID查询开团记录
 *     19.根据groupID查询跟团记录
 *     20.根据userID查询地址集合
 *     21.根据AddressID查询具体的address信息
 *     22.据userID查询团购信息
 *     23.根据userID查询参团信息
 *     24.参团后当前人数+1
 * </p>
 *
 */

public interface GroupBuyingDAO {
    /**
     * 根据查询条件查询符合条件的所有团购活动
     * @param activity 查询条件封装在此对象中
     * @return 返回符合条件的所有团购活动集合
     */
    public ArrayList<Activity> searchActivities(Activity activity);



    /**
     * 根据团购活动号查询参与此团购活动的队伍
     * @param activityID 团购活动ID，用于唯一区别团购活动
     * @return 返回参与团购活动的队伍集合
     */
    public ArrayList<Group> searchGroups(int activityID);


    /**
     * 开团操作
     * 在团购队伍表中新增一条记录
     * @param group  新增的团购队伍的信息封装在此对象中
     */
    public void initiateGroupBuying(Group group);


    /**
     * 获得最新创建的group的ID
     * @return 返回刚创建的group的ID
     */
    public int getGroupID();


    /**
     * 开团附属操作，生成开团记录
     * @param map 收获地址等信息封装在map里
     */
    public void initiateGroupBuyingSuffix(Map<String, Object> map);

    /**
     * 发布团购活动
     * 在团购活动表中新添一条数据
     * @param activity  新增数据涉及的属性封装在Activity对象中
     */
    public void releaseActivity(Activity activity);

    /**
     * 跟团
     * 在跟团表中新增一条数据
     * @param joinGroupList 新增跟团数据涉及的属性封装在JoinGroupList对象中
     */
    public void joinGroupBuying(JoinGroupList joinGroupList);

    /**
     * 检查是否已经达到要求参与的人数
     * DAO层做的主要工作就是返回当前人数
     * @param groupID 团购队伍ID,用于唯一区分团购队伍
     * @return 返回此groupID对应的团购队伍的当前参与人数
     */
    public int checkIfEnough(int groupID);


    /**
     * 配合上面的函数checkIfEnough
     * 此函数的作用在于返回活动预先设定的需要的参与人数
     * @param groupID 团购队伍ID,用于唯一区分团购队伍
     * @return 返回此groupID对应的团购队伍对应的团购活动的实际需要的人数
     */
    public int getRequiredNumber(int groupID);


    /**
     * 检查是否可以删除，如果已经开始就不能删除，DAO层做的工作就是返回其开始时间
     * @param activity 查询条件activityID封装在Activity对象中
     * @return 返回此activityID对应的团购活动的开始时间
     */
    public Date checkIfCanDelete(Activity activity);

    /**
     * 根据活动ID批量删除团购活动，具体做法为将团购活动的状态置为0（被删除）
     * @param checkID 要删除的团购活动的活动ID数组
     */
    public void deleteActivities(int[] checkID);

    /**
     * 根据groupID查询joiner
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有的跟团者的ID集合
     */
    public ArrayList<Integer> searchJoiners(int groupID);

    /**
     * 根据groupID查询leader
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回此团队下所有的开团者的ID集合
     */
    public int searchLeader(int groupID);



    /**
     * 根据groupID查询团购队伍的相关信息
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 返回该团队ID对应的团队的相关信息，封装在Group对象中
     */
    public Group searchGroupInfo(int groupID);

    /**
     * 根据activityID查询团购活动的相关信息
     * @param activityID 活动ID，用于唯一区分团购活动ID
     * @return 返回该活动ID对应的团购活动的相关信息，封装在Activity对象中
     */
    public Activity searchActivityInfo(int activityID);

    /**
     * 将团购活动按照团购价格升序，返回升序集合
     * @return 返回团购活动按照团购价格升序后的集合
     */
    public ArrayList<Activity> getRecommendedGroupBuying();

    /**
     * 修改团购信息
     * @param activity 修改后的团购活动信息封装在Activity对象中
     */
    public void modifyActivityInfo(Activity activity);

    /**
     * 根据gourpID查询开团记录
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 回此团队下所有开团记录的集合
     */
    public ArrayList<OpenGroupList> getOpenGroupList(int groupID);

    /**
     * 根据groupID查询跟团记录
     * @param groupID 团队ID，用于唯一区分团购团队
     * @return 回此团队下所有跟团记录的集合
     */
    public ArrayList<JoinGroupList> getJoinGroupList(int groupID);

    /**
     * 根据userID查询地址集合
     * @param userID 用户ID，用于唯一区分用户
     * @return 该用户下所有的地址集合
     */
    public ArrayList<Address> getAddress(int userID);

    /**
     * 根据AddressID查询具体的address信息
     * @param addressID 地址ID，用于唯一区分地址
     * @return 该地址ID对应的具体的地址信息
     */
    public Address searchAddressInfo(int addressID);

    /**
     * 据userID查询团购信息
     * @param userID  用户ID，用于唯一区分用户
     * @return 返回该用户的所有团购队伍记录的集合
     */
    public ArrayList<Group> searchGroupsByUserID(int userID);

    /**
     * 根据userID查询参团信息
     * @param userID 用户ID，用于唯一区分用户
     * @return 返回该用户的所有参团记录的集合
     */
    public ArrayList<JoinGroupList> searchJoinGroupListByUserID(int userID);


    /**
     * 参团后当前人数+1
     * @param joinGroupList 参团记录ID封装在JoinGroupList对象中
     *
     */
    public void updateCurrentNumber(JoinGroupList joinGroupList);

    /**
     * 统计团购商品的参团人数
     * @return
     */
    public ArrayList<ActivityForCount> countActivities();
}

