package com.ssm.controller;

import com.ssm.model.service.AfterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AfterController {
@Autowired
    private AfterService afterService;
}
