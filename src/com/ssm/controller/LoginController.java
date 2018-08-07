package com.ssm.controller;

import com.ssm.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("login/{userId}/{password}")
    @ResponseBody
    public boolean login(@PathVariable int userId,@PathVariable String password){
        return loginService.login(userId,password);
    }
}
