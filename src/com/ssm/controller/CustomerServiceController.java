package com.ssm.controller;

import com.ssm.model.bean.CustomerService;
import com.ssm.model.service.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerServiceController {
    @Autowired
    private CustomerServiceService customerService;
}
