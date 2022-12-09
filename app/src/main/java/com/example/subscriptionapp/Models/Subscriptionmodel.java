package com.example.subscriptionapp.Models;

public class Subscriptionmodel {

    String name, type, price, date, status;

    public Subscriptionmodel() {
    }

    public Subscriptionmodel(String name, String type, String price, String date, String status) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.date = date;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
