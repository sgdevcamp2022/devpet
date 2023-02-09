package com.example.petmily.model.data.chat.room.remote;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//방을 처음 생성할 때 아마 프로필로 가져갈 예정
public class ChatRoomMake {

    @SerializedName("roomId")
    private String roomId;

    @Embedded
    @SerializedName("userId")
    private List<String> userId;

    @SerializedName("userCount")
    private long userCount; // 채팅방 인원

    public ChatRoomMake(String roomId, List<String> userId, long userCount) {
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
