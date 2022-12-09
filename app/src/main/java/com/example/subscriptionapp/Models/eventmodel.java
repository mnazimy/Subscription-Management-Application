package com.example.subscriptionapp.Models;

public class eventmodel {

    String id , event, date, name, mobnumber, address;

    public eventmodel() {
    }


    public eventmodel(String id, String event, String date, String name, String mobnumber, String address) {
        this.id = id;
        this.event = event;
        this.date = date;
        this.name = name;
        this.mobnumber = mobnumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobnumber() {
        return mobnumber;
    }

    public void setMobnumber(String mobnumber) {
        this.mobnumber = mobnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
