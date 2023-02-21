package com.example.petmily.model.data.post;

import android.net.Uri;

import java.util.List;

import retrofit2.http.Url;

public class PostGrid {

    //String image;
    Uri uri;
    String nickname;
    String text;
    String time;


    public PostGrid(Uri uri, String nickname, String text, String time) {
        this.uri = uri;
        this.nickname = nickname;
        this.text = text;
        this.time = time;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
