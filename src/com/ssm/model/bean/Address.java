package com.ssm.model.bean;

public class Address {
    private int addressID;
    private String address;
    private int postcode;
    private long acceptPhone;
    private String acceptName;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
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

    public long getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(long acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }
}

