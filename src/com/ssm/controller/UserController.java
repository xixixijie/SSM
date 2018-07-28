package com.ssm.controller;

import java.util.List;

import com.ssm.model.bean.Bankcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.model.bean.UserInfo;
import com.ssm.model.service.UserService;

@Controller
public class UserController {
	//实现依赖注入
	@Autowired
	private UserService userService;


	@RequestMapping(value = "addUser")
	@ResponseBody
	public String addUser(UserInfo user){
		System.out.println("-----增加用户Controller-----");
		userService.addUser(user);
		return "{\"result\":true}";
	}

	@RequestMapping(value = "getUser/{userID}",produces={"application/json;","text/html;charset=UTF-8;"})
	@ResponseBody
	public UserInfo getUser(@PathVariable int userID){
		System.out.println("-----获得用户Controller-----");
		return userService.getUser(userID);
	}

	@RequestMapping(value = "editUser")
	@ResponseBody
	public String editUser(UserInfo user){
		System.out.println("-----修改用户Controller-----");
		userService.editUser(user);
		// mav.addObject("resultList",list);
		return "{\"result\":true}";
	}

	@RequestMapping(value = "editPassword")
	@ResponseBody
	public String editPassword(UserInfo user){
		System.out.println("-----修改密码Controller-----");
		userService.editPassword(user);
		// mav.addObject("resultList",list);
		return "{\"result\":true}";
	}

    @RequestMapping(value = "editAvatar")
    @ResponseBody
    public String editAvatar(UserInfo user){
        System.out.println("-----修改头像Controller-----");
        userService.editAvatar(user);
        // mav.addObject("resultList",list);
        return "{\"result\":true}";
    }

    @RequestMapping(value = "userLogin")
    @ResponseBody
    public String userLogin(UserInfo user){
        System.out.println("-----用户登录Controller-----");
        userService.userLogin(user);
        // mav.addObject("resultList",list);
        return "{\"result\":true}";
    }

	@RequestMapping(value = "addBankcard")
	@ResponseBody
	public String addBankcard(Bankcard bankcard){
		System.out.println("-----增加银行卡Controller-----");
		userService.addBankcard(bankcard);
		return "{\"result\":true}";
	}

	@RequestMapping(value = "deleteBankcard")
	@ResponseBody
	public String deleteBankcard(int bankcardID){
		System.out.println("-----删除银行卡Controller-----");
		userService.deleteBankcard(bankcardID);
		return "{\"result\":true}";
	}
}
