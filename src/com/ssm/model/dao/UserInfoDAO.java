package com.ssm.model.dao;

import java.util.List;

import com.ssm.model.bean.Bankcard;
import com.ssm.model.bean.UserInfo;

public interface UserInfoDAO {
	public void addUser(UserInfo user);
	public void editUser(UserInfo user);
	public UserInfo getUser(int userID);
	public void editPassword(UserInfo user);
	public void editAvatar(UserInfo user);
	public void userLogin(UserInfo user);
	public void addBankcard(Bankcard bankcard);
	public void deleteBankcard(int bankcardID);
}
