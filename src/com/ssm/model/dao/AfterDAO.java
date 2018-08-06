package com.ssm.model.dao;

import com.ssm.model.bean.AfterServiceList;

public interface AfterDAO {
    public AfterServiceList getAfterDetail(int after_id);
    public void addAfterService(AfterServiceList as);
}
