package com.ssm.model.service;

import com.ssm.model.bean.Message;
import com.ssm.model.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyufeng on 2018/7/24.
 */

/**
 * <p>该类用于执行消息模块的相关操作,主要包括根据userID查询用户的消息，
 * 将已经先显示过的信息设为已读，向user_message表中插入新数据，根据messageID查询message的具体body等功能</p>
 *
 *
 * @see MessageDAO 消息通知模块的DAO层
 * @author chenyufeng
 *
 */

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    /**
     * 根据userID查询用户的消息
     * @param userID 用户ID，唯一标识区分用户
     * @return  返回根据userID查询到的对应用户的消息集合
     */
    public ArrayList<Message> getMessage(int userID) {
        return messageDAO.getMessage(userID);
    }

    /**
     * 将已经显示过的信息设为已读
     * @param messageID  消息的ID，唯一标识区分消息
     */
    public void messageRead(int messageID) {
        messageDAO.messageRead(messageID);
    }


    /**
     * 根据userID，messageTitle,messageBody往user_message数据表中插入数据
     * @param userID   用户的ID，唯一标识区分用户
     * @param messageTitle 消息标题
     * @param messageBody 具体的消息体
     */
    public void addMessage(int userID, String messageTitle, String messageBody) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userID",userID);
        map.put("messageTitle",messageTitle);
        map.put("messageBody",messageBody);
        messageDAO.addMessage(map);
    }


    /**
     * 根据messageID查询message的具体body
     * @param messageID 消息的ID，唯一标识区分消息
     * @return 返回message的具体body，封装在Message对象中
     */
    public Message searchMessageInfo(int messageID) {
        return messageDAO.searchMessageInfo(messageID);
    }
}
