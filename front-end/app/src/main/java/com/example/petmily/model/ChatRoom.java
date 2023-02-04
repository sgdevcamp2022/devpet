package com.example.petmily.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRoom {
    @SerializedName("roomId")
    public String roomId;

    @SerializedName("senderName")
    public String senderName;

    @SerializedName("receiverName")
    public String receiverName;

    @SerializedName("messages")
    public List<ChatMessage> messages;

    @SerializedName("timelog")
    public String timelog;

    public ChatRoom(String roomId, String senderName, String receiverName, List<ChatMessage> messages, String timelog) {
        this.roomId = roomId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.messages = messages;
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

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }
}
