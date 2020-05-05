package com.example.apeschat.models;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class MyLocation {

    private String userId;
    private GeoPoint geo_point;
    private @ServerTimestamp Date timestamp;

    public MyLocation() {
    }

    public MyLocation(String userId, GeoPoint geo_point, Date timestamp) {
        this.userId = userId;
        this.geo_point = geo_point;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return userId;
    }

    public void setUser(String user) {
        this.userId = user;
    }

    public GeoPoint getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
