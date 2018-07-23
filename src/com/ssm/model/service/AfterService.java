package com.ssm.model.service;

import com.ssm.model.dao.AfterDAO;
import com.ssm.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfterService {
    @Autowired
    private AfterDAO afterDAO;
}
