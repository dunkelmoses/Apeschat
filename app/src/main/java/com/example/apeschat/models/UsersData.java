package com.example.apeschat.models;

public class UsersData {
    private String username;
    private String userID;
    private String email;
    private String fullName;

    public UsersData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
