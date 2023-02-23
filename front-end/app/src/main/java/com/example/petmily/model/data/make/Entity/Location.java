package com.example.petmily.model.data.make.Entity;

public class Location {
    int category;
    Coord coord;

    public Location(int category, Coord coord) {
        this.category = category;
        this.coord = coord;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
