package com.example.cis_marketplace;

public class Listing {
    String buyerID;
    String condition;
    String description;
    String id;
    String imagePath;
    String name;
    String ownerID;
    double price;
    String state;
    String subject;
    String type;
    int yearLevel;

    public Listing() {

    }
    public Listing(String buyerID, String condition, String description, String id, String imagePath, String name, String ownerID, double price, String state, String subject, String type, int yearLevel) {
        this.buyerID = buyerID;
        this.condition = condition;
        this.description = description;
        this.id = id;
        this.imagePath = imagePath;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public double getPrice() {
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

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
}
