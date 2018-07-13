package com.ssm.model.dao;

import java.util.List;

import com.ssm.model.bean.UserInfo;

public interface UserInfoDAO {
	public List<UserInfo> selectUsers();
}
