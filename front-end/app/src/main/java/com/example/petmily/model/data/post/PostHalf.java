package com.example.petmily.model.data.post;

import android.net.Uri;

import com.example.petmily.model.data.post.Entity.Coord;

import retrofit2.http.Url;

public class PostHalf {

    Coord coord;
    String nickname;
    Uri imageUri;
    //int image;

    public PostHalf(Coord coord, String nickname, Uri imageUri) {
        this.coord = coord;
        this.nickname = nickname;
        this.imageUri = imageUri;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getPlacename() {
        return nickname;
    }

    public void setPlacename(String nickname) {
        this.nickname = nickname;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
