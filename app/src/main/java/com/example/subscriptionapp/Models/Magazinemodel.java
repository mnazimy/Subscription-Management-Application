package com.example.subscriptionapp.Models;

public class Magazinemodel {

    String name, language, type, status, price, frequency;

    public Magazinemodel(String name, String language, String type, String status, String price, String frequency) {
        this.name = name;
        this.language = language;
        this.type = type;
        this.status = status;
        this.price = price;
        this.frequency = frequency;
    }

    public Magazinemodel() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
