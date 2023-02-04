package com.example.petmily.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChatRoom")
public class ChatContentSQL {

    @PrimaryKey
    @ColumnInfo(name = "roodId")
    String roodId;

    @ColumnInfo(name = "timeLog")
    String timeLog;

    @ColumnInfo(name = "senderNickname")
    String senderNickname;

    @ColumnInfo(name = "profileImage")
    String profileImage;

    @ColumnInfo(name = "sender")
    String sender;

    public ChatContentSQL(String timeLog, String senderNickname, String profileImage, String roodId, String sender) {
        this.timeLog = timeLog;
        this.senderNickname = senderNickname;
        this.profileImage = profileImage;
        this.roodId = roodId;
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
