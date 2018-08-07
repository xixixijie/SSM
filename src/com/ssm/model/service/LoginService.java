package com.ssm.model.service;

import com.ssm.model.dao.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private LoginDAO loginDAO;
    public boolean login(int userId, String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("password",password);
        int count=loginDAO.login(map);
        if(count==0){
            return false;
        }else {
            return true;
        }
    }
}
