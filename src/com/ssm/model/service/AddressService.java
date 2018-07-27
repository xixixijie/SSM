package com.ssm.model.service;

import com.ssm.model.bean.Address;
import com.ssm.model.dao.AddressDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressDAO addressDAO;

    public void deleteAddress(int addressID) {
        System.out.println("-----删除地址service-----");
        addressDAO.deleteAddress(addressID);
    }

    public void addAddress(Address address) {
        System.out.println("-----添加地址service-----");
        addressDAO.addAddress(address);

    }

    public Address getAddress(int AddressID) {
        System.out.println("-----获取地址service-----");
        return addressDAO.getAddress(AddressID);

    }

    public void editAddress(Address address) {
//        System.out.println(classify.getClassifyID());
//        System.out.println(classify.getClassName());
//        System.out.println(classify.getClassDis());
//        System.out.println(classify.getClassState());
        System.out.println("-----修改地址service-----");
        addressDAO.editAddress(address);

    }

    public List<Address> showAddress() {
        System.out.println("-----展示地址service-----");
        List<Address> list = addressDAO.showAddress();
        return list;

    }
}
