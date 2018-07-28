package com.ssm.controller;

import com.ssm.model.bean.Message;
import com.ssm.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by chenyufeng on 2018/7/24.
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 根据userID查询用户的消息
     * @param userID
     * @return
     */
    @RequestMapping(value = "/getMessage/{userID}" ,method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Message> getMessage(@PathVariable int userID){
        ArrayList<Message> messages = messageService.getMessage(userID);
        return messages;
    }

    /**
     * 将已经显示过的信息设为已读
     * @param messageID
     */
    @RequestMapping(value = "/readMessage/{messageID}" ,method = RequestMethod.POST)
    @ResponseBody
    public void messageRead(@PathVariable int messageID){
        messageService.messageRead(messageID);
    }



}
