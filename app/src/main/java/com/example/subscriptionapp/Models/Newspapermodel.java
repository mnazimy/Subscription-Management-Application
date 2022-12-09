package com.example.subscriptionapp.Models;

public class Newspapermodel {

    String name, language, status, price;

    public Newspapermodel(String name, String language, String status, String price) {
        this.name = name;
        this.language = language;
        this.status = status;
        this.price = price;
    }

    public Newspapermodel() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
