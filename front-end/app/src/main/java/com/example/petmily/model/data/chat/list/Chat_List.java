package com.example.petmily.model.data.chat.list;


public class Chat_List {

    int profile_image;
    String nickname;
    String chat;
    int count;
    String time;
    String roomId;


    public Chat_List(int profile_image, String nickname, String chat) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.chat = chat;
    }

    public Chat_List(int profile_image, String nickname, String chat, int count, String time) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.chat = chat;
        this.count = count;
        this.time = time;
        this.roomId = "133a8c93-7952-4e7d-8891-dc4758f554eb";
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}