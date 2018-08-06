package com.ssm.controller;

import com.ssm.model.bean.AfterOrder;
import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PersonalController {

    @Autowired
    private PersonalService personalService;
    @RequestMapping(value = "/getFinish/{userId}", method = RequestMethod.POST)
    //userId是客户的id
    @ResponseBody
    public List<AfterOrder> getFinishOrder(@PathVariable int userId) {
        System.out.println("显示已完成订单");
        return personalService.getFinishOrder(userId);
    }
    @RequestMapping(value="/getAfter/{userId}",method=RequestMethod.POST)
    @ResponseBody
    public List<AfterServiceList> getAfter(@PathVariable int userId) {
        System.out.println("获得售后申请列表");
        List<AfterServiceList> list=personalService.getAfter(userId);
        return list;
    }
}
