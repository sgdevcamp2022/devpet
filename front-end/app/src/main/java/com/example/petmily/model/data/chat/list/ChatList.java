package com.example.petmily.model.data.chat.list;


public class ChatList {

    public String roodId;

    public String timeLog;

    public String senderNickname;

    public String profileImage;

    public String sender;

    public int count;

    public String lastText;

    public String alarm;

    public ChatList(String roodId, String timeLog, String senderNickname, String profileImage, String sender, int count, String lastText, String alarm) {
        this.roodId = roodId;
        this.timeLog = timeLog;
        this.senderNickname = senderNickname;
        this.profileImage = profileImage;
        this.sender = sender;
        this.count = count;
        this.lastText = lastText;
        this.alarm = alarm;
    }

    public String getRoodId() {
        return roodId;
    }

    public void setRoodId(String roodId) {
        this.roodId = roodId;
    }

    public String getTimeLog() {
        return timeLog;
    }

    public void setTimeLog(String timeLog) {
        this.timeLog = timeLog;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}