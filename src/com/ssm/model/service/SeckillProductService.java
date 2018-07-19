package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.dao.SeckillProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillProductService {
    @Autowired
    private SeckillProductDAO dao;
    public List<Classify>  selectAllClassifies(){
        return dao.selectAllClassifies();
    }
}
