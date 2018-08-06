package com.ssm.model.service;

import com.ssm.model.bean.ActivityForCount;
import com.ssm.model.dao.GroupBuyingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chenyufeng on 2018/8/6.
 */
@Service
public class CountService {

    @Autowired
    private GroupBuyingDAO groupBuyingDAO;

    public ArrayList<ActivityForCount> getActivityForCount() {
        //首先获得activityID和参团人数
        ArrayList<ActivityForCount> activityForCounts = groupBuyingDAO.countActivities();
        //然后把整个activity对象获得
        for (Iterator<ActivityForCount> activityForCountIterator = activityForCounts.iterator();activityForCountIterator.hasNext();){
            ActivityForCount activityForCount = activityForCountIterator.next();
            activityForCount.setActivity(groupBuyingDAO.searchActivityInfo(activityForCount.getActivityID()));
        }
        return activityForCounts;
    }
}
