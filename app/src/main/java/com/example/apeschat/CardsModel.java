package com.example.apeschat;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardsModel {
    private String name, age, gender, lookingFor,bio;
    private String image;

    public CardsModel() {
    }

    public CardsModel(String name, String age, String gender, String lookingFor, String bio, String image) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.lookingFor = lookingFor;
        this.bio = bio;
        this.image = image;
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