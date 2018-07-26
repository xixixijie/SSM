package com.ssm.model.dao;

import java.util.List;

import com.ssm.model.bean.UserInfo;

public interface UserInfoDAO {
	public List<UserInfo> selectUsers();
	public void addUser(UserInfo user);

    int getUsername(int userID);
}
