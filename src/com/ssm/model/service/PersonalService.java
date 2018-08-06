package com.ssm.model.service;

import com.ssm.model.bean.AfterOrder;
import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.dao.PersonalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService {
    @Autowired
    private PersonalDAO personalDAO;

    public List<AfterServiceList> getAfter(int userId){
        List<AfterServiceList> list =new ArrayList<>();
        list=personalDAO.getAfter(userId);
        return list;
    }
    public List<AfterOrder> getFinishOrder(int userId){
        return  personalDAO.getFinishOrder(userId);
    }
}
