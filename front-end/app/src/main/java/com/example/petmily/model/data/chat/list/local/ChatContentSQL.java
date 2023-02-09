package com.example.petmily.model.data.chat.list.local;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChatContentSQL")
public class ChatContentSQL {

    @NonNull
    @PrimaryKey
    public String roodId;

    public String timeLog;

    public String senderNickname;

    public String profileImage;

    public String sender;


    public ChatContentSQL(@NonNull String roodId, String timeLog, String senderNickname, String profileImage, String sender) {
        this.roodId = roodId;
        this.timeLog = timeLog;
        this.senderNickname = senderNickname;
        this.profileImage = profileImage;
        this.sender = sender;
    }

    public String getTimeLog() {
        return timeLog;
    }

    public void setTimeLog(String timeLog) {
        this.timeLog = timeLog;
    }

    public String getNickname() {
        return senderNickname;
    }

    public void setNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRoodId() {
        return roodId;
    }

    public void setRoodId(String roodId) {
        this.roodId = roodId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}


