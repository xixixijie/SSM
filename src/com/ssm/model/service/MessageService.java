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
@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    /**
     * 根据userID查询用户的消息
     * @param userID
     * @return
     */
    public ArrayList<Message> getMessage(int userID) {
        return messageDAO.getMessage(userID);
    }

    /**
     * 将已经显示过的信息设为已读
     * @param messageID
     */
    public void messageRead(int messageID) {
        messageDAO.messageRead(messageID);
    }


    /**
     * 根据userID，messageTitle,messageBody往user_message数据表中插入数据
     * @param userID
     * @param messageTitle
     * @param messageBody
     */
    public void addMessage(int userID, String messageTitle, String messageBody) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userID",userID);
        map.put("messageTitle",messageTitle);
        map.put("messageBody",messageBody);
        messageDAO.addMessage(map);
    }


}
