package com.example.petmily.model.data.profile.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProfileSQL")
public class ProfileSQL {

    @NonNull
    @PrimaryKey
    String name;

    String nickname;

    String about;

    public ProfileSQL(@NonNull String name, String nickname, String about) {
        this.name = name;
        this.nickname = nickname;
        this.about = about;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
}
