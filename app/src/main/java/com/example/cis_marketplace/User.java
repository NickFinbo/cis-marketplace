package com.example.cis_marketplace;

public class User {
    String name;
    String email;
    String subjects;
    String phoneNumber;
    String profileImagePath; // equals to userID
    String yearLevel;
    String [] userListings;

    public User() {
    }

    public User(String name, String email, String subjects, String phoneNumber, String profileImagePath, String yearLevel, String[] userListings) {
        this.name = name;
        this.email = email;
        this.subjects = subjects;
        this.phoneNumber = phoneNumber;
        this.profileImagePath = profileImagePath;
        this.yearLevel = yearLevel;
        this.userListings = userListings;
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

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String[] getUserListings() {
        return userListings;
    }

    public void setUserListings(String[] userListings) {
        this.userListings = userListings;
    }

}

