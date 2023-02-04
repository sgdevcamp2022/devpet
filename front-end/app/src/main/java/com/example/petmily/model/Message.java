package com.example.petmily.model;

public class Message {

    //ChatRoomSQL과 동일한 내용이 들어갈 예정
    int profile_image;
    String nickname;
    String last_text;
    String check;
    String roodId;

    public Message(int profile_image, String nickname, String last_text, String check, String roodId) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.last_text = last_text;
        this.check = check;
        this.roodId = roodId;
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

    public String getRoodId() {
        return roodId;
    }

    public void setRoodId(String roodId) {
        this.roodId = roodId;
    }
}
