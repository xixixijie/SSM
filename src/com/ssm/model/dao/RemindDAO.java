package com.ssm.model.dao;

import com.ssm.model.bean.Remind;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface RemindDAO {

    //获取所有的下一场秒杀的提醒
    List<Remind> getAllRemind(Map<String, Object> map);

    //插入到user_message表中数据

}
