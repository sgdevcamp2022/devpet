package com.example.petmily.model.data.make.Entity;

public class Coord {
    double latitude;
    double longitude;

    public Coord(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double lonngitude) {
        this.longitude = lonngitude;
    }
}
