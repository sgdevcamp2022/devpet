package com.example.petmily.model;

public class Message {
    int profile_image;
    String nickname;
    String last_text;
    String check;

    public Message(int profile_image, String nickname, String last_text, String check) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.last_text = last_text;
        this.check = check;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLast_text() {
        return last_text;
    }

    public void setLast_text(String last_text) {
        this.last_text = last_text;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
