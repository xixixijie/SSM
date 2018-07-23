package com.ssm.model.dao;

import com.ssm.model.bean.AfterServiceList;

import java.util.List;

public interface PersonalDAO {
    public List<AfterServiceList> getAfter(int userId);
}
