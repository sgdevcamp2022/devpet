package com.example.petmily.model;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestModel {


    /*
    private String type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String receiver;    //메시지 받는사람
    private String message; // 메시지
    private String timeLog;

     */
    @SerializedName("roomId")
    private String roomId;

    @Embedded
    @SerializedName("userId")
    private List<String> userId;

    @SerializedName("userCount")
    private long userCount; // 채팅방 인원수

    public TestModel(String roomId, List<String> userId, long userCount) {
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
