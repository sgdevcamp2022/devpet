package com.example.petmily.model;

import java.util.List;

public class TestChatRoom {

    private String roomId;
    private List<String> userId;
    private long userCount; // 채팅방 인원

    public TestChatRoom(String roomId, List<String> userId, long userCount) {
        this.roomId = roomId;
        this.userId = userId;
        this.userCount = userCount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }
}
