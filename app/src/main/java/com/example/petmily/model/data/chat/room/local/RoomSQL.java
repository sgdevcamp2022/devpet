package com.example.petmily.model.data.chat.room.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.petmily.model.data.chat.room.Message;

import java.util.List;


@Entity(tableName = "RoomSQL")
public class RoomSQL {

    @NonNull
    @PrimaryKey
    public String roomId;

    public String senderName;

    public String receiverName;

    public List<Message> messages;

    public String timeLog;


    public RoomSQL(@NonNull String roomId, String senderName, String receiverName, List<Message> messages, String timeLog) {
        this.roomId = roomId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.messages = messages;
        this.timeLog = timeLog;
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

    public String getTimeLog() {
        return timeLog;
    }

    public void setTimeLog(String timeLog) {
        this.timeLog = timeLog;
    }
}


