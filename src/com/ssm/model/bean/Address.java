package com.ssm.model.bean;

public class Address {
    private int address_ID;
    private String address;
    private int postcode;
    private long accept_Phone;
    private String accept_Name;
    private int status;

    public int getAddress_ID() {
        return address_ID;
    }

    public void setAddress_ID(int address_ID) {
        this.address_ID = address_ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public long getAccept_Phone() {
        return accept_Phone;
    }

    public void setAccept_Phone(long accept_Phone) {
        this.accept_Phone = accept_Phone;
    }

    public String getAccept_Name() {
        return accept_Name;
    }

    public void setAccept_Name(String accept_Name) {
        this.accept_Name = accept_Name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

