package com.ssm.model.bean;

/**
 * Created by chenyufeng on 2018/7/24.
 */

/**
 * <p>
 *     消息实体类
 *     messageID  消息的ID，唯一标识区分消息
 *     user       消息是发给哪一个用户的，用户信息封装在里面
 *     messageTitle  消息标题，消息的简单归纳
 *     messageBody   具体的消息部分
 *     isRead        是否已读，0表示未读，1表示已读
 * </p>
 *
 * @author chenyufeng
 */
public class Message {
    public int messageID;
    public UserInfo user;
    public String messageTitle;
    public String messageBody;
    public int isRead;

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
