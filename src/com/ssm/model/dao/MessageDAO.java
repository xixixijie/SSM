package com.ssm.model.dao;

import com.ssm.model.bean.Message;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/24.
 */
public interface MessageDAO {
    /**
     * 根据userID查询用户的消息
     * @param userID
     * @return
     */
    public ArrayList<Message> getMessage(int userID);

    /**
     * 将已经显示过的信息设为已读
     * @param messageID
     */
    public void messageRead(int messageID);

    /**
     * 根据userID，messageTitle,messageBody往user_message数据表中插入数据
     * @param map
     */
    public void addMessage(Map<String, Object> map);

}
