package com.example.apeschat;

public class TestModel {
    private String name, id, image;

    public TestModel(String name, String id, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public TestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
