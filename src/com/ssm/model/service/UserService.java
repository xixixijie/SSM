package com.ssm.model.service;

import com.ssm.model.bean.Bankcard;
import com.ssm.model.bean.UserInfo;
import com.ssm.model.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;

	public void addUser(UserInfo user){
		userInfoDAO.addUser(user);
	}

	public void editUser(UserInfo user){
		userInfoDAO.editUser(user);
	}

	public UserInfo getUser(int userID){
		return userInfoDAO.getUser(userID);
	}

	public void editPassword(UserInfo user){
		userInfoDAO.editPassword(user);
	}

	public void editAvatar(UserInfo user){
		userInfoDAO.editAvatar(user);
	}

	public void userLogin(UserInfo user){
		userInfoDAO.userLogin(user);
	}

	public void addBankcard(Bankcard bankcard){
		userInfoDAO.addBankcard(bankcard);
	}

	public void deleteBankcard(int bankcardID){
		userInfoDAO.deleteBankcard(bankcardID);
	}
}
