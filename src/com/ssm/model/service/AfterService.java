package com.ssm.model.service;

import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.dao.AfterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfterService {
    @Autowired
    private AfterDAO afterDAO;
    public AfterServiceList getAfterDetail(int after_id){
        System.out.println("service查询详情");
        return afterDAO.getAfterDetail(after_id);
    }
    public void addAfterService(AfterServiceList as){
        afterDAO.addAfterService(as);
    }
}
