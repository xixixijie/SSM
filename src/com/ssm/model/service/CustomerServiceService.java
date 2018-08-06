package com.ssm.model.service;

import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.dao.CustomerServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceService {
    @Autowired
    private CustomerServiceDAO customerServiceDAO;
    public List<AfterServiceList> getUntreatAfter(){
        List<AfterServiceList> list =new ArrayList<>();
        list=customerServiceDAO.getUntreatAfter();
        return list;
    }
    public void updateAfter(AfterServiceList as){
         customerServiceDAO.updateAfter(as);
    }
    public AfterServiceList getUntreatAfterDetail(int after_id) {
        return  customerServiceDAO.getUntreatAfterDetail(after_id);

    }
}
