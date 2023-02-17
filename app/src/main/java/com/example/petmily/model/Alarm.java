package com.example.petmily.model;

public class Alarm {
    int profile_image;
    String nickname;
    String alarm_text;

    public Alarm(int profile_image, String nickname, String alarm_text) {
        this.profile_image = profile_image;
        this.nickname = nickname;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAlarm_text() {
        return alarm_text;
    }

    public void setAlarm_text(String alarm_text) {
        this.alarm_text = alarm_text;
    }

}
