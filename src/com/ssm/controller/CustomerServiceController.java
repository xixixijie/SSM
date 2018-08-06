package com.ssm.controller;

import com.ssm.model.bean.AfterServiceList;
import com.ssm.model.service.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class CustomerServiceController {
    @Autowired
    private CustomerServiceService customerService;
    @RequestMapping(value="/getUntreatAfter")
    @ResponseBody
    public List<AfterServiceList> getUntreatAfter(){
        return customerService.getUntreatAfter();
    }
    @RequestMapping(value="/getUntreatDetail/{after_id}")
    @ResponseBody
    public AfterServiceList getUntreatDetail(@PathVariable int after_id){
        return customerService.getUntreatAfterDetail(after_id);
    }
    @RequestMapping(value="/updateAfter/{after_id}/{result}/{cs_id}")
    @ResponseBody
    public String  updateAfter(@PathVariable int after_id, @PathVariable String result, @PathVariable int cs_id){
        System.out.println("更新服务单");
        String newResult=null;
        try {
            newResult = URLDecoder.decode(result,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AfterServiceList as=new AfterServiceList();
        as.setAfter_id(after_id);
        as.setResult(newResult);
        as.setCs_id(cs_id);
        as.setStatus(1);
        customerService.updateAfter(as);
        return "{\"result\":true}";
    }
}
