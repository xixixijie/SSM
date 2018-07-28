package com.ssm.model.dao;

import com.ssm.model.bean.Address;

import java.util.List;

public interface AddressDAO {
    public void deleteAddress(int addressID);

    public void addAddress(Address address);

    public Address getAddress(int addressID);

    public void editAddress(Address address);


    public List<Address> showAddress();


}
