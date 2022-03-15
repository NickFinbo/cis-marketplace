package com.example.cis_marketplace.Lucas;

import java.util.ArrayList;

public class User {
    String name;
    String email;
    ArrayList<String> subjects;
    String phoneNumber;
    String yearLevel;
    String id;

    public User() {
    }

    public User(String name, String email, ArrayList<String> subjects, String phoneNumber, String yearLevel, String id) {
        this.name = name;
        this.email = email;
        this.subjects = subjects;
        this.phoneNumber = phoneNumber;
        this.yearLevel = yearLevel;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

