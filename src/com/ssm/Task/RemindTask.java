package com.ssm.Task;

import com.ssm.model.bean.Product;
import com.ssm.model.bean.Remind;
import com.ssm.model.dao.MessageDAO;
import com.ssm.model.dao.RemindDAO;
import com.ssm.model.dao.SeckillProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RemindTask {
    @Autowired
    //获取提醒dao
    private RemindDAO remindDAO;
    //messagedao进行消息添加
    @Autowired
    private MessageDAO messageDAO;
    //将当前时间的提醒全部从数据库取出来并设置到用户的提醒中
    public void remindJob(){
        //获取当前时间，并计算出下一次要秒杀的时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour+1;
        Timestamp timestamp = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+"00:00");
        //Timestamp timestamp = Timestamp.valueOf(year+"-"+month+"-"+day+" "+"12"+":"+"00:00");
        System.out.println(timestamp);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("timestamp",timestamp);
        //找到特定时间所有的remind
        List<Remind> list =remindDAO.getAllRemind(map);

        //添加提醒
        for(Remind r:list){
            Map<String,Object> messageMap = new HashMap<String,Object>();
            messageMap.put("userID",r.getUser_id());
            messageMap.put("messageTitle","秒杀提醒");
            messageMap.put("messageBody","你关注的秒杀商品"+r.getSeckillProduct().getProduct().getProduct_name()+"即将在15分钟后参与秒杀，请合分配时间");
            messageDAO.addMessage(messageMap);
        }
        /*
        Map<String,Object> messageMap = new HashMap<String,Object>();
        messageMap.put("userID",1);
        messageMap.put("messageTitle","秒杀提醒");
        messageMap.put("messageBody","你关注的秒杀商品"+"即将在15分钟后参与秒杀，请合分配时间");
        messageDAO.addMessage(messageMap);*/

    }

}
