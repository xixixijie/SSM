package com.ssm.model.dao;

import com.ssm.model.bean.AfterServiceList;

import java.util.List;

public interface CustomerServiceDAO {
    public List<AfterServiceList> getUntreatAfter();
    public void updateAfter(AfterServiceList as);
    public AfterServiceList getUntreatAfterDetail(int after_id);
}
