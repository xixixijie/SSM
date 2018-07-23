package com.ssm.model.service;

import com.ssm.model.dao.CustomerServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceService {
    @Autowired
    private CustomerServiceDAO customerServiceDAO;
}
