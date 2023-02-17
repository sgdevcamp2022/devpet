package com.example.petmily.model.data.post;

import android.net.Uri;

import com.example.petmily.model.data.post.Entity.Coord;

import retrofit2.http.Url;

public class PostHalf {

    Coord coord;
    String placename;
    Uri imageUri;
    //int image;

    public PostHalf(Coord coord, String placename, Uri imageUri) {
        this.coord = coord;
        this.placename = placename;
        this.imageUri = imageUri;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
