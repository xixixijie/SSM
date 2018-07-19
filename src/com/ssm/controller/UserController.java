package com.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.model.bean.UserInfo;
import com.ssm.model.service.UserService;

@Controller
public class UserController {
	//实现依赖注入
	@Autowired
	private UserService userService;
	
	@RequestMapping("selectUser")
	public ModelAndView selectUser() {
		System.out.println("inhaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		//调用service进行查询
		List<UserInfo> list = userService.selectUsers();
		ModelAndView mav = new ModelAndView();
		mav.addObject("resultList",list);
		mav.setViewName("select");
		return mav;
	}
}
