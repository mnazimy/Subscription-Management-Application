package com.example.subscriptionapp.Models;

public class Usermodel {

    String name, phone, mail, password, address, town, city, pincode, state, id;

    public Usermodel() {
    }

    public Usermodel(String name, String phone
            , String mail, String password
            , String address, String town
            , String city, String pincode
            , String state, String id) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.town = town;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
