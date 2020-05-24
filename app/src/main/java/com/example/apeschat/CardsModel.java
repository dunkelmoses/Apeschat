package com.example.apeschat;

import androidx.annotation.NonNull;


public class CardsModel {
    private String name, age, gender, lookingFor, bio, id, image;
    private CardsModel user;
    public CardsModel() {
    }

    public CardsModel(String name, String age, String gender, String lookingFor, String bio, String id, String image) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.lookingFor = lookingFor;
        this.bio = bio;
        this.id = id;
        this.image = image;
    }

    public CardsModel getUser() {
        return user;
    }

    public void setUser(CardsModel user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}