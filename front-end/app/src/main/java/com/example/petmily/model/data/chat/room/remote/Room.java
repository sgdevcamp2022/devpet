package com.example.petmily.model.data.chat.room.remote;


import com.example.petmily.model.data.chat.room.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Room {
    @SerializedName("roomId")
    public String roomId;

    @SerializedName("senderName")
    public String senderName;

    @SerializedName("receiverName")
    public String receiverName;

    @SerializedName("messages")
    public String message;

    @SerializedName("timelog")
    public String timelog;

    public Room(String roomId, String senderName, String receiverName, String message, String timelog) {
        this.roomId = roomId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.message = message;
        this.timelog = timelog;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }
}
