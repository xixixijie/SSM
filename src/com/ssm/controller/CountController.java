package com.ssm.controller;

import com.ssm.model.bean.ActivityForCount;
import com.ssm.model.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by chenyufeng on 2018/8/6.
 */
@Controller
public class CountController {
    @Autowired
    private CountService countService;

    @RequestMapping(value = "/getActivityCount", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<ActivityForCount> getActivityForCount(){
        return countService.getActivityForCount();
    }
}
