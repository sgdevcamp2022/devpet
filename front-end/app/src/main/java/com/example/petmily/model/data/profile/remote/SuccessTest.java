package com.example.petmily.model.data.profile.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessTest {
    String nickname;

    String about;

    String birth;

    public SuccessTest(String nickname, String about, String birth) {
        this.nickname = nickname;
        this.about = about;
        this.birth = birth;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
