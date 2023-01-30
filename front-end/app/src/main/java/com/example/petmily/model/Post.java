package com.example.petmily.model;

public class Post {
    int image;
    String nickname;
    String text;
    String time;

    public Post(int image, String nickname, String text, String time) {
        this.image = image;
        this.nickname = nickname;
        this.text = text;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
