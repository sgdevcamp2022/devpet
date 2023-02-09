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
    public List<Message> messages;

    @SerializedName("timelog")
    public String timelog;

    public Room(String roomId, String senderName, String receiverName, List<Message> messages, String timelog) {
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomId='" + roomId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", messages=" + messages +
                ", timelog='" + timelog + '\'' +
                '}';
    }
}
