package com.example.cis_marketplace.Lucas;

import java.io.Serializable;

public class Listing implements Serializable {
    String buyerID;
    String condition;
    String description;
    String id;
    String name;
    String ownerID;
    double price;
    String state;
    String subject;
    String type;
    int yearLevel;

    public Listing() {

    }

    public Listing(String condition, String description, String id, String name, String ownerID, double price, String state, String subject, String type, int yearLevel) {
        this.buyerID = null;
        this.condition = condition;
        this.description = description;
        this.id = id;
        this.name = name;
        this.ownerID = ownerID;
        this.price = price;
        this.state = state;
        this.subject = subject;
        this.type = type;
        this.yearLevel = yearLevel;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
}
