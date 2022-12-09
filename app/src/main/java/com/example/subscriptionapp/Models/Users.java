package com.example.subscriptionapp.Models;

public class Users {

    String name, mail, password, mobilenumber, uid, vendorid ;

    //Empty constructor for firebase database
    public Users() {
    }

    public Users(String name, String mail, String password, String mobilenumber, String uid, String vendorid) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.mobilenumber = mobilenumber;
        this.uid = uid;
        this.vendorid = vendorid;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
