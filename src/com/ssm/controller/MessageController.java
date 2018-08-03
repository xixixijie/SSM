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

/**
 *  <p>
 *      消息通知模块的Controller层，涉及的主要操作有
 *      1.根据userID查询用户的消息
 *      2.将已经显示过的信息设为已读
 *      3.根据messageID查询message的具体body
 *  </p>
 *
 *  @see MessageService  消息通知模块的Service层
 *  @author chenyufeng
 *
 */

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 根据userID查询用户的消息
     * @param userID 用户ID，用于唯一区别用户
     * @return 返回此用户ID对应的用户的消息集合
     */
    @RequestMapping(value = "/getMessage/{userID}" ,method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Message> getMessage(@PathVariable int userID){
        ArrayList<Message> messages = messageService.getMessage(userID);
        return messages;
    }

    /**
     * 将已经显示过的信息设为已读
     * 将Message的isRead属性置为1
     * @param messageID 消息ID，用于唯一区别一条消息
     */
    @RequestMapping(value = "/readMessage/{messageID}" ,method = RequestMethod.POST)
    @ResponseBody
    public void messageRead(@PathVariable int messageID){
        messageService.messageRead(messageID);
    }


    /**
     * 根据messageID查询message的具体body
     * @param messageID 消息ID，用于唯一区别一条消息
     * @return 返回此messageID对应的消息的具体body,封装在Message对象中
     */
    @RequestMapping(value = "/searchMessageInfo/{messageID}" ,method = RequestMethod.POST)
    @ResponseBody
    public Message searchMessageInfo(@PathVariable int messageID){
        return messageService.searchMessageInfo(messageID);
    }



}
