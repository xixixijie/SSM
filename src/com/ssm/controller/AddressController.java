package com.ssm.controller;

import com.ssm.model.bean.Address;
import com.ssm.model.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "deleteAddress/{addressID}")
    @ResponseBody
    public String deleteAddress(@PathVariable int addressID){
        System.out.println("-----删除地址Controller-----");
        addressService.deleteAddress(addressID);
        return "{\"result\":true}";

    }

    @RequestMapping(value = "addAddress")
    @ResponseBody
    public String addAddress(Address address){
        System.out.println("-----增加地址Controller-----");
        addressService.addAddress(address);
        return "{\"result\":true}";

    }

    @RequestMapping(value = "getAddress/{addressID}",produces={"application/json;","text/html;charset=UTF-8;"})
    @ResponseBody
    public Address getAddress(@PathVariable int addressID){
        System.out.println("-----获得地址Controller-----");
        return addressService.getAddress(addressID);
    }

    @RequestMapping(value = "editAddress")
    @ResponseBody
    public String editAddress(Address address){
        System.out.println("-----修改地址Controller-----");
        addressService.editAddress(address);
        // mav.addObject("resultList",list);
        return "{\"result\":true}";

    }
    @RequestMapping(value = "showAddress")
    @ResponseBody
    public List<Address> showAddress(){
        System.out.println("-----展示地址Controller-----");
//        System.out.println(type);
        return addressService.showAddress();

    }
}
