package com.ssm.model.dao;

import com.ssm.model.bean.Message;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/24.
 */


/**
 * <p>
 * 消息通知模块的DAO层，涉及的主要操作有
 * 1.根据userID查询用户的消息
 * 2.将已经显示过的信息设为已读
 * 3.根据messageID查询message的具体body
 * </p>
 */
public interface MessageDAO {
    /**
     * 根据userID查询用户的消息
     * @param userID 用户ID，用于唯一区别用户
     * @return 返回此用户ID对应的用户的消息集合
     */
    public ArrayList<Message> getMessage(int userID);

    /**
     * 将已经显示过的信息设为已读
     * 将Message的isRead属性置为1
     * @param messageID 消息ID，用于唯一区别一条消息
     */
    public void messageRead(int messageID);

    /**
     * 根据userID，messageTitle,messageBody往user_message数据表中插入数据
     * 插入一条新的信息所需的属性封装在map中
     * @param map
     */
    public void addMessage(Map<String, Object> map);

    /**
     * 根据messageID查询message的具体body
     * @param messageID 消息ID，用于唯一区别一条消息
     * @return 返回此messageID对应的消息的具体body,封装在Message对象中
     */
    public Message searchMessageInfo(int messageID);
}
