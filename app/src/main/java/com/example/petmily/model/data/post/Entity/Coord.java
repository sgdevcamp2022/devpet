package com.example.petmily.model.data.post.Entity;

public class Coord {

    double latitude;
    double lonngitude;

    public Coord(double latitude, double lonngitude) {
        this.latitude = latitude;
        this.lonngitude = lonngitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLonngitude() {
        return lonngitude;
    }

    public void setLonngitude(double lonngitude) {
        this.lonngitude = lonngitude;
    }
}
