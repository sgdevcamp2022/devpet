package com.example.petmily.model.data.post.Entity;


public class Location {

    int locationId;
    String name;
    String address;
    String category;
    Coord coord;

    public Location(int locationId, String name, String address, String category, Coord coord) {
        this.locationId = locationId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.coord = coord;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}

