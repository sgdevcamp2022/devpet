package com.example.petmily.model;

public class Chat {

    int profile_image;
    String nickname;
    String chat;
    int count;
    String time;


    public Chat(int profile_image, String nickname, String chat) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.chat = chat;
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

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
