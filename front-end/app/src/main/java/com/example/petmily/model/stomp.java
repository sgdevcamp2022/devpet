package com.example.petmily.model;

public class stomp {




    String roomId;
    String roomName;
    String message;
    String messages[];
    String token;
    int chatCount;

    public stomp(String roomId, String roomName, String message, String[] messages, String token, int chatCount) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.message = message;
        this.messages = messages;
        this.token = token;
        this.chatCount = chatCount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getChatCount() {
        return chatCount;
    }

    public void setChatCount(int chatCount) {
        this.chatCount = chatCount;
    }
}
