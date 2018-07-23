package com.ssm.controller;

import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.bean.UserInfo;
import com.ssm.model.service.PersonalService;
import com.ssm.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class PersonalController {

    @Autowired
    private PersonalService personalService;
    @RequestMapping(value = "/getFinish/{userId}", method = RequestMethod.POST)
    //userId是客户的id
    @ResponseBody
    public ModelAndView getFinishedOrder(@PathVariable int userId) {
        System.out.println("显示已完成订单");
        //通过用户编号获得已完成订单列表，直接调用别人的功能
//        List<UserInfo> list = userService.selectUsers();
        ModelAndView mav = new ModelAndView();
//        mav.addObject("resultList",list);
//        mav.setViewName("select");
       return mav;
    }
    @RequestMapping(value="/getAfter/{userId}",method=RequestMethod.POST)
    @ResponseBody
    public ModelAndView getAfter(@PathVariable int userId) {
        System.out.println("获得售后申请列表");
        List<AfterServiceList> list=personalService.getAfter(userId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("resultList",list);
        mav.setViewName("AfterServiceList");
        return mav;
    }
}
