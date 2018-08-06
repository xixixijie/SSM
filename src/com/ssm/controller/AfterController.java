package com.ssm.controller;

import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.service.AfterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class AfterController {
@Autowired
    private AfterService afterService;
@RequestMapping(value = "/getAfterDetail/{after_id}")
@ResponseBody
    private AfterServiceList getAfterDetail(@PathVariable int after_id){
    System.out.println("获取服务单详细信息");
    AfterServiceList as=afterService.getAfterDetail(after_id);
    return as;
}
@RequestMapping(value="/addAfter/{user_id}/{reason}/{requirement}/{order_id}")
@ResponseBody
    public String addAfter(@PathVariable int user_id, @PathVariable String reason, @PathVariable String requirement, @PathVariable int order_id){
    System.out.println("新增售后服务单");
    String newReason=null;
    String newRequirement=null;
    try {
        newReason = URLDecoder.decode(reason,"UTF-8");
        newRequirement=URLDecoder.decode(requirement,"UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    AfterServiceList as=new AfterServiceList();
    as.setUser_id(user_id);
    as.setReason(newReason);
    as.setRequirement(newRequirement);
    as.setOrder_id(order_id);
    afterService.addAfterService(as);
    return "{\"result\":true}";
}
}
