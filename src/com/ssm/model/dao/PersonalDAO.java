package com.ssm.model.dao;

import com.ssm.model.bean.AfterOrder;
import com.ssm.model.bean.AfterServiceList;

import java.util.List;

public interface PersonalDAO {
    public List<AfterServiceList> getAfter(int user_id);
    public List<AfterOrder> getFinishOrder(int user_id);
}
