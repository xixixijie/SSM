package com.ssm.model.service;

import com.ssm.model.bean.UserInfo;
import com.ssm.model.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;

	public List<UserInfo> selectUsers(){
		return userInfoDAO.selectUsers();
	}
}
