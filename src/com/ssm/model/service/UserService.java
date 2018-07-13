package com.ssm.model.service;

import com.ssm.model.bean.*;
import com.ssm.model.dao.UserInfoDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	public List<UserInfo> selectUsers(){
		return userInfoDAO.selectUsers();
	}
}
